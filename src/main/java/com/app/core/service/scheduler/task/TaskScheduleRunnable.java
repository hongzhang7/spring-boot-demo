package com.app.core.service.scheduler.task;

import com.app.common.dal.auto.dataobject.SimpleTaskRepoDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 调度线程
 *
 * @author wb-zh270168
 */
public class TaskScheduleRunnable implements Runnable {

    public static final Logger logger = LoggerFactory.getLogger(TaskScheduleRunnable.class);

    /**
     * 线程池线程不足时的等候时间
     */
    private static final long WAIT_INTERNAL = 10000L;

    /**
     * 线程池
     */
    private final ThreadPoolExecutor executorService;

    private final SimpleTask simpleTask;

    private final TaskFactory taskFactory;

    private final TaskLifecycleCallback callback;

    private final ShutDownMonitor monitor;

    /**
     * 构造函数
     *
     * @param simpleTask
     * @param taskFactory
     */
    public TaskScheduleRunnable(SimpleTask simpleTask, TaskFactory taskFactory) {
        this.simpleTask = simpleTask;
        this.taskFactory = taskFactory;
        // 初始化线程池
        executorService = new ThreadPoolExecutor(4, 4, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        monitor = new ShutDownMonitor();
        // 为调度线程加入监听--待定
        Runtime.getRuntime().addShutdownHook(monitor);
        // 线程回调
        callback = new TaskLifecycleCallbackImpl(simpleTask);
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        while(!monitor.isShutDown()) {
            if (executorService.getActiveCount() >= executorService.getCorePoolSize()) {
                if (logger.isInfoEnabled()) {
                    logger.info("线程池不足, 进入等待状态");
                }
                try {
                    // 进入睡眠
                    Thread.sleep(WAIT_INTERNAL);
                } catch (InterruptedException e) {
                    logger.warn("中断线程等待", e.getMessage());
                }
                continue;
            }

            Task task = pollTask();
            if (task == null) {
                if (logger.isInfoEnabled()) {
                    logger.info("未获取到有效任务, 进入等待状态");
                }
                try {
                    // 进入睡眠
                    Thread.sleep(WAIT_INTERNAL);
                } catch (InterruptedException e) {
                    logger.warn("中断线程等待", e.getMessage());
                }
                continue;
            }
            submitTask(task);
        }

        executorService.shutdown();
        try {
            // 等待线程池关闭 20s
            executorService.awaitTermination(20, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.warn("等待线程池关闭", e.getMessage());
        }

        monitor.markFinish();
    }

    /**
     * 将任务添加到线程池中
     *
     * @param task
     */
    private void submitTask(Task task) {
        // 创建任务线程
        Runnable runnable = TaskExecuteRunnable.create(task, callback);
        // 添加到线程池中
        executorService.execute(runnable);
    }

    /**
     * 等待中的任务
     *
     * @return
     */
    private Task pollTask() {
        while (true) {
            SimpleTaskRepoDO taskRepoDO = simpleTask.obtainTask();
            if (taskRepoDO == null) {
                // 无法获取任务, 说明当前基类的所有任务已经消耗完毕
                logger.warn("暂未发现任务积压");
                return null;
            }
            // 创建任务对象
            Task task = taskFactory.buildTask(taskRepoDO);
            if (task == null) {
                logger.warn("任务无法实例化", taskRepoDO);
                return null;
            } else if (task.isReady() && simpleTask.grabTask(task.getTaskId())) {
                logger.warn("任务获取成功: {}", taskRepoDO);
                return task;
            }
        }
    }

}
