package com.app.core.service.scheduler.task;

import com.app.common.dal.auto.dataobject.SimpleTaskRepoDO;

import java.util.Date;

/**
 * 任务执行
 *
 */
public interface SimpleTask {

    /**
     * 框架启动时初始化
     */
    void init();

    /**
     * 任务加入调度框架
     *
     * @param task
     * @return
     */
    Long scheduleTask(Task task);

    /**
     * 期待触发任务的时间
     *
     * @param task
     * @param time
     * @return
     */
    Long scheduleTask(Task task, Date time);

    /**
     * 获取调度任务
     *
     * @return
     */
    SimpleTaskRepoDO obtainTask();

    /**
     * 抢占调度任务
     *
     * @param taskId
     * @return
     */
    boolean grabTask(long taskId);

    /**
     * 任务执行成功回调
     *
     * @param task
     */
    void onTaskSuccess(Task task);

    /**
     * 任务执行失败回调
     *
     * @param task
     */
    void onTaskError(Task task);

    /**
     * 任务延后执行回调
     *
     * @param task
     * @param delayWhenRetry
     * @return
     */
    boolean onRetryTask(Task task, int delayWhenRetry);

    /**
     * 任务状态置为取消
     *
     * @param taskId
     */
    void cancel(Long taskId);

    /**
     * 更新任务状态
     *
     * @param task
     */
    void updateTask(Task task);

    /**
     * 重置任务状态至 WAITING
     *
     * @param taskId
     */
    void resetTask(long taskId);

    /**
     * 变更任务的执行机器
     *
     * @param taskId
     * @param machine
     */
    void changeTaskMachine(Long taskId, String machine);

    /**
     * 判断任务是否在指定状态
     *
     * @param task
     * @param status
     * @return
     */
    boolean isTaskInStatus(Task task, TaskStatus status);

    /**
     * 判断是否有相同的任务还在执行中
     * @param type
     * @param taskId
     * @return
     */
    boolean hasSameTypeRemaining(int type, long taskId);

    /**
     * 获取当前指定任务类型数量
     *
     * @return
     */
    Integer getRemainingTaskCount(int value);

}
