package com.app.core.service.scheduler.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 *
 * @author wb-zh270168
 */
public class TaskLifecycleCallbackImpl implements  TaskLifecycleCallback {

    private static final Logger logger = LoggerFactory.getLogger(TaskLifecycleCallbackImpl.class);

    private final SimpleTask simpleTask;

    /**
     * 构造函数
     * @param simpleTask
     */
    public TaskLifecycleCallbackImpl(SimpleTask simpleTask) {
        this.simpleTask = simpleTask;
    }

    /**
     * 任务开始执行
     *
     * @param task
     */
    @Override
    public void onTaskExecuteStart(Task task) {
        logger.warn("任务开始执行");
    }

    /**
     * 任务停止执行
     *
     * @param task
     */
    @Override
    public void onTaskExecuteStop(Task task, TaskStatus status) {
        logger.warn("任务停止执行");
        // 更新任务状态
        simpleTask.updateTask(task);
        switch (status) {
            case SUCCESS:
                try {
                    // 任务线程调用成功方法
                    task.onTaskSuccess();
                } catch (Exception e) {
                    logger.error("调度任务失败", e);
                }
                // 调度任务调用成功方法
                simpleTask.onTaskSuccess(task);
                break;
            case FAIL:
                try {
                    // 任务线程调用失败方法
                    task.onTaskFail();
                } catch (Exception e) {
                    logger.error("任务调度执行失败", e);
                }
                // 调度任务调用失败方法
                simpleTask.onTaskError(task);
                break;
            default:
                break;
        }
    }

    /**
     * 任务重试
     *
     * @param task
     */
    @Override
    public boolean onTaskScheduleRetry(Task task) {
        logger.warn("任务重试");
        return simpleTask.onRetryTask(task, task.getDelayWhenRetry());
    }

}
