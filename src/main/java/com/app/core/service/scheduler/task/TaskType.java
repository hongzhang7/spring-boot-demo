package com.app.core.service.scheduler.task;

import java.util.Objects;

/**
 * 任务类型
 *
 * @author wb-zh270168
 */
public enum TaskType {

    /**
     * 人群发布人群计算任务
     */
    TYPE_CROUND_COUNT(1, CrowdCountComputationTask.class),

    /**
     * 人群导出任务
     */
    @Deprecated
    TYPE_EXPORT_CROWD(2, null),

    /**
     * 人群回流至客群表中
     */
    @Deprecated
    TYPE_ODPS_TRANSFER(3, null),

    /**
     * 问卷投放任务
     */
    TYPE_SURVEY_PUBLISH(4, SurveyPublishTask.class),

    /**
     * 客群计算是否轮询完毕
     */
    TYPE_GROUP_POLLING(5, null),

    TYPE_ODPS_2_NOMO(6, null),

    TYPE_PUBLISH_COMPUTATION(7, ScenePublishTask.class),

    TYPE_ODPS_2_HISTORE(8, null),

    TYPE_NOMO_TIMED_TASK(9, NomoTimedTask.class),

    TYPE_SCENE_NOTIFICATION_TASK(10, null),

    TYPE_SURVEY_RETROSPECTION_TASK(11, SurveyAnswerRestrospectionTask.class),

    TYPE_SURVEY_REPUBLISH(12, null),

    TYPE_ANSWER_EXPORT(13, AnswerResultExportTask.class),

    TYPE_CROWD_FILTER(14, CrowdFilterTask.class),

    TYPE_JOB_ACTIVE(15, JobArchiveTask.class);

    private final int value;

    private final Class<? extends Task> taskClazz;

    /**
     * 构造函数
     * @param value
     * @param taskClazz
     */
    TaskType(int value, Class<? extends Task> taskClazz) {
        this.value = value;
        this.taskClazz = taskClazz;
    }

    public int getValue() {
        return value;
    }

    public Class<? extends Task> getTaskClazz() {
        return taskClazz;
    }

    /**
     * 根据 value 返回 TaskType
     * @param value
     * @return
     */
    public static TaskType valueOf(int value) {
        for (TaskType type : values()) {
            if (Objects.equals(type.value, value)) {
                return type;
            }
        }
        return null;
    }
}
