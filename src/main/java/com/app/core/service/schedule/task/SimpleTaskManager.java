package com.app.core.service.scheduler.task;

import com.alibaba.fastjson.JSONObject;
import com.app.common.dal.auto.daointerface.SimpleTaskRepoDAO;
import com.app.common.dal.auto.dataobject.SimpleTaskRepoDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.net.InetAddress;
import java.util.Date;
import java.util.Objects;

/**
 * 简单的任务调度, 后续改为分布式任务调度
 *
 * @author wb-zh270168
 */
public class SimpleTaskManager implements SimpleTask, InitializingBean {

    public static final Logger logger = LoggerFactory.getLogger(SimpleTaskManager.class);

    /**
     * 预发机器前缀
     */
    private static final String PRE_MACHINE_HOSTNAME_PREFIX = "mindv-99";

    /**
     * 灰度机器前缀
     */
    private static final String GREY_MACHINE_HOSTNAME_PREFIX = "mindv-8";

    private SimpleTaskRepoDAO simpleTaskRepoDAO;

    private TaskFactory factory;

    private String testProfile;

    /**
     * 当前机器的 hostname 用于标明任务处理机器
     */
    private String hostname;

    public void setSimpleTaskRepoDAO(SimpleTaskRepoDAO simpleTaskRepoDAO) {
        this.simpleTaskRepoDAO = simpleTaskRepoDAO;
    }

    public void setFactory(TaskFactory factory) {
        this.factory = factory;
    }

    /**
     * Invoked by a BeanFactory after it has set all bean properties supplied
     * (and satisfied BeanFactoryAware and ApplicationContextAware).
     * <p>This method allows the bean instance to perform initialization only
     * possible when all bean properties have been set and to throw an
     * exception in the event of misconfiguration.
     *
     * @throws Exception in the event of misconfiguration (such
     *                   as failure to set an essential property) or if initialization fails.
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        logger.warn("框架启动后执行调度任务初始化");
        if ("true".equals(testProfile)) {
            // 测试环境不需要启动系统
            hostname = "test";
            return;
        }
        hostname = (InetAddress.getLocalHost()).getHostName();
        init();
    }

    @Override
    public void init() {
        // 初始化时, 通常是在任务重启时触发, 此时需要将之前 RUNNING 状态的任务修改为 RETRY, 等待重启状态
        logger.warn("初始化调度任务");
        simpleTaskRepoDAO.resetRunningTaskWhenRestart(hostname);
        startSimpleTaskJob();
    }

    /**
     * 启动任务调度管理器
     */
    private void startSimpleTaskJob() {
        logger.warn("启动任务调度管理器");
        new Thread(new TaskScheduleRunnable(this, factory)).start();
    }

    /**
     * 判断是预发环境还是灰度环境
     *
     * @return
     */
    private boolean isPreOrGrab() {
        return hostname.startsWith(PRE_MACHINE_HOSTNAME_PREFIX) || hostname.startsWith(GREY_MACHINE_HOSTNAME_PREFIX);
    }

    /**
     * 任务加入调度框架
     *
     * @param task
     * @return
     */
    @Override
    public Long scheduleTask(Task task) {
        logger.warn("任务 {} 加入调度框架", task);
        return scheduleTask(task, null);
    }

    /**
     * 期待触发的任务时间
     *
     * @param task
     * @param time
     * @return
     */
    @Override
    public Long scheduleTask(Task task, Date time) {
        logger.warn("任务 {} 加入调度框架, 在期待时间 {} 触发", task, time);
        if (task == null) {
            return 0L;
        }
        if (time == null) {
            time = new Date();
        }
        logger.warn("任务加入调度序列: {} 任务配置: {} at time: {}", task.getType(), task.getConfig(), time);
        // 创建任务数据
        SimpleTaskRepoDO simpleTaskRepoDO = new SimpleTaskRepoDO();
        simpleTaskRepoDO.setStatus(TaskStatus.WAITING.getValue());
        simpleTaskRepoDO.setType(task.getType());
        JSONObject config = task.getConfig();
        if (config != null) {
            simpleTaskRepoDO.setConfig(config.toJSONString());
        }
        // 初始化设定 machine 为空, 可以供其他任何节点机器获取
        if (isPreOrGrab()) {
            simpleTaskRepoDO.setMachine(hostname);
        } else {
            simpleTaskRepoDO.setMachine("");
        }
        simpleTaskRepoDO.setTriggerTime(time);
        return simpleTaskRepoDAO.insert(simpleTaskRepoDO);
    }

    /**
     * 获取调度任务
     *
     * @return
     */
    @Override
    public SimpleTaskRepoDO obtainTask() {
        logger.warn("获取调度任务");
        SimpleTaskRepoDO task;
        // 获取任务加锁
        synchronized (SimpleTaskManager.class) {
            // 若当前机器不需要执行的任务, 则去获取空任务, 避免由于高频任务堆积, 导致任务长时间不处理的情况
            SimpleTaskRepoDO tmpTask;
            if (!isPreOrGrab()) {
                tmpTask = simpleTaskRepoDAO.getNotAsignedTask("", DateUtils.addMinute(new Date()), -5);
                if (tmpTask != null) {
                    // 尝试将该任务授予当前机器
                    simpleTaskRepoDAO.asgineMachine(hostname, tmpTask.getId(), "");
                }
            }
            // 只获取距离上次 modified 超过 30s 的任务
            // 优先获取已归属于当前机器的任务
            task = simpleTaskRepoDAO.obtainTask(hostname);
            while (task == null
                    // 灰度和预发机器不需要主动去获取任务
                    && !isPreOrGrab()
                    && ((tmpTask = simpleTaskRepoDAO.getNotAsignedTask("", new Date())) != null)) {
                logger.warn("抢占任务 {} {}", hostname, tmpTask.getId());
                // 尝试将该任务授予当前机器
                simpleTaskRepoDAO.asgineMachine(hostname, tmpTask.getId(), "");
                task = simpleTaskRepoDAO.obtainTask(hostname);
            }
            if (task != null) {
                // 获取任务以后更新 gmt_trigger 防止总是消费同一个任务
                simpleTaskRepoDAO.refreshTriggerTime(DateUtils.addMinute(new Date(), 1), task.getId());
            }
        }
        return task;
    }

    /**
     * 抢占调度任务
     *
     * @param taskId
     * @return 成功返回 true, 失败返回 false
     */
    @Override
    public boolean grabTask(long taskId) {
        logger.warn("抢占调度任务 {}", taskId);
        synchronized (SimpleTaskManager.class) {
            int status = simpleTaskRepoDAO.getStatusByTaskId(taskId);
            if (status != TaskStatus.WAITING.getValue() && status != TaskStatus.RETRY.getValue()) {
                logger.warn("任务已被抢占 {}", taskId);
                return false;
            } else {
                simpleTaskRepoDAO.setTaskRunning(taskId);
            }
        }
        return true;
    }

    /**
     * 任务执行成功回调
     *
     * @param task
     */
    @Override
    public void onTaskSuccess(Task task) {
        logger.warn("任务 {} 执行成功回调", task);
        synchronized (SimpleTaskManager.class) {
            int status = simpleTaskRepoDAO.getStatusByStaskId(task.getTaskId());
            if (status != TaskStatus.RUNNING.getValue()) {
                logger.warn("只有运行中的任务才能被修改 {}", task.toString());
            } else {
                simpleTaskRepoDAO.setTaskSuccess(task.getTaskId());
            }
        }
    }

    /**
     * 任务执行失败回调
     *
     * @param task
     */
    @Override
    public void onTaskError(Task task) {
        logger.warn("任务 {} 执行失败回调", task);
        synchronized (SimpleTaskManager.class){
            int status = simpleTaskRepoDAO.getStatusByTaskId(task.getTaskId());
            if (status != TaskStatus.RUNNING.getValue()) {
                logger.warn("只有运行中的任务才能被修改 {}", task.toString());
            } else {
                simpleTaskRepoDAO.setTaskFail(task.getTaskId());
            }
        }
    }

    /**
     * 任务延后执行回调
     *
     * @param task
     * @param delayWhenRetry
     * @return
     */
    @Override
    public boolean onRetryTask(Task task, int delayWhenRetry) {
        logger.warn("任务 {} 延后执行回调, 延后时间", task, delayWhenRetry);
        synchronized (SimpleTaskManager.class) {
            // 只获取距离上次 modified 超过 30s 的任务
            Date triggerTime = new Date(System.currentTimeMillis() + delayWhenRetry * 60 * 1000);
            SimpleTaskRepoDO taskRepoDO = simpleTaskRepoDAO.getById(task.getTaskId());
            if (task.getTimeout() != -1
                    && DateUtils.addMinute(taskRepoDO.getGmtCreate(), task.getTimeout()).before(new Date())) {
                logger.error("超过最大超时分钟数 {}", task.toString());
            } else if (task.getMaxRetryCount() != -1 && task.getMaxRetryCount() < taskRepoDO.getRetryCount()) {
                logger.error("超过最大允许次数 {}", task.toString());
            } else if (taskRepoDO.getStatus() != TaskStatus.RUNNING.getValue()) {
                logger.error("只有运行中的任务才能被修改 {}", task.toString());
            } else {
                simpleTaskRepoDAO.retryTaskLater(triggerTime, task.getTaskId());
                return true;
            }
        }
        return false;
    }

    /**
     * 任务状态置为取消
     *
     * @param taskId
     */
    @Override
    public void cancel(Long taskId) {
        logger.warn("取消任务执行 {}", taskId);
        simpleTaskRepoDAO.cancel(taskId);
    }

    /**
     * 更新任务状态
     *
     * @param task
     */
    @Override
    public void updateTask(Task task) {
        logger.warn("更新任务 {} 状态", task);
        JSONObject config = task.getConfig();
        simpleTaskRepoDAO.updateTaskConfig(config != null ? config.toJSONString() : null, task.getTaskId());
    }

    /**
     * 重置任务状态至 WAITING
     *
     * @param taskId
     */
    @Override
    public void resetTask(long taskId) {
        logger.warn("将任务 {} 重置为等待状态, 重新执行", taskId);
        simpleTaskRepoDAO.resetTaskToWaiting(taskId);
    }

    /**
     * 变更任务的执行机器
     *
     * @param taskId
     * @param machine
     */
    @Override
    public void changeTaskMachine(Long taskId, String machine) {
        logger.warn("变更任务 {} 执行的机器 {}", taskId, machine);
        simpleTaskRepoDAO.changeTaskMachine(machine, taskId);
    }

    /**
     * 判断任务是否在指定状态
     *
     * @param task
     * @param status
     * @return
     */
    @Override
    public boolean isTaskInStatus(Task task, TaskStatus status) {
        logger.warn("判断任务 {} 是否在指定状态 {}", task, status);
        Integer statusValue = status.getValue();
        return Objects.equals(status.getValue(), statusValue);
    }

    /**
     * 判断是否有相同的任务还在执行中
     * @param type
     * @param taskId
     * @return
     */
    @Override
    public boolean hasSameTypeRemaining(int type, long taskId) {
        logger.warn("是否有相同的任务 {} 在执行当中 {}", taskId, type);
        Boolean result = simpleTaskRepoDAO.hasSameTypeRemaining(type, taskId);
        return Boolean.TRUE.equals(result);
    }

    /**
     * 获取当前指定任务类型数量
     *
     * @param value
     * @return
     */
    @Override
    public Integer getRemainingTaskCount(int value) {
        logger.warn("获取当前指定任务类型数量");
        return simpleTaskRepoDAO.getRemainingTaskCount(value);
    }
}
