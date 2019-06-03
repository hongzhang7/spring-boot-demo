package com.app.core.service.scheduler.task;

/**
 * 需要获取对应的 TaskId
 * <p>
 * 在 TaskFactory 中会检测到 TaskIdAware 属性，进行 taskId 的注入
 *
 * @author xunwu.zy
 * @see TaskFactory
 */
public interface TaskIdAware {

    /**
     * 设置 TaskId
     *
     * @param taskId
     */
    void setTaskId(long taskId);

    /**
     * 获取 TaskId
     *
     * @return
     */
    long getTaskId();
}
