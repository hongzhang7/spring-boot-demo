package com.app.core.service.scheduler.task;

/**
 * 任务状态信息
 *
 * @author wb-zh270168
 */
public enum TaskStatus {
    /**
     * 队列中等待执行
     */
    WAITING(0),

    /**
     * 正在执行
     */
    RUNNING(1),

    /**
     * 正在重试
     */
    RETRY(2),

    /**
     * 执行成功
     */
    SUCCESS(3),

    /**
     * 执行失败
     */
    FAIL(4),

    /**
     * 取消执行
     */
    CANCEL(5);

    private final int value;

    TaskStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    /**
     * 根据 value 反推 TaskStatus
     *
     * @param value
     * @return
     */
    public static TaskStatus valueOf(int value) {
        for (TaskStatus status : values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        return null;
    }

}
