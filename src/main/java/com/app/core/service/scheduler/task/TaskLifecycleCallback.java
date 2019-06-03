package com.app.core.service.scheduler.task;

/**
 *
 *
 * @author wb-zh270168
 */
public interface TaskLifecycleCallback {

    /**
     * 任务开始执行
     *
     * @param task
     */
    void onTaskExecuteStart(Task task);

    /**
     * 任务停止执行
     *
     * @param task
     * @param status
     */
    void onTaskExecuteStop(Task task, TaskStatus status);

    /**
     * 任务重试
     *
     * @param task
     * @return
     */
    boolean onTaskScheduleRetry(Task task);
}
