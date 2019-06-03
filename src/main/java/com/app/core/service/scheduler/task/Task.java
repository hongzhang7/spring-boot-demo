package com.app.core.service.scheduler.task;

import com.alibaba.fastjson.JSONObject;

/**
 * 任务接口
 *
 * @author xunwu.zy
 */
public interface Task extends TaskIdAware {

    /**
     * 获取任务类型
     *
     * @return
     */
    int getType();

    /**
     * 获取任务配置
     *
     * @return
     */
    JSONObject getConfig();

    /**
     * 通过配置还原任务
     *
     * @param config
     */
    void restoreConfig(JSONObject config);

    /**
     * 任务执行, 并返回状态结果
     *
     * @return
     */
    TaskStatus execute();

    /**
     * 获取最大重试次数
     *
     * @return
     */
    int getMaxRetryCount();

    /**
     * 获取重试时, 延后的时间, 单位: 分钟
     *
     * @return
     */
    int getDelayWhenRetry();

    /**
     * 获取超时时间, 单位: 分钟
     *
     * @return
     */
    int getTimeout();

    /**
     * 任务成功处理
     */
    void onTaskSuccess();

    /**
     * 任务失败处理
     *
     */
    void onTaskFail();

    /**
     * 判断任务是否准备完毕
     *
     * @return
     */
    boolean isReady();

}
