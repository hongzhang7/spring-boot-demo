package com.app.core.service.scheduler.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.app.common.dal.auto.dataobject.SimpleTaskRepoDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 任务构造工厂
 *
 * @author xunwu.zy
 */
public class TaskFactory implements ApplicationContextAware {

    public static final Logger logger = LoggerFactory.getLogger(TaskFactory.class);

    private AutowireCapableBeanFactory factory;

    /**
     * Set the ApplicationContext that this object runs in.
     * Normally this call will be used to initialize the object.
     * <p>Invoked after population of normal bean properties but before an init callback such
     * as {@link InitializingBean#afterPropertiesSet()}
     *
     * @param applicationContext the ApplicationContext object to be used by this object
     * @throws BeansException if thrown by application context methods
     * @see BeanInitializationException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.factory = applicationContext.getAutowireCapableBeanFactory();
    }

    /**
     * 构造任务对象
     *
     * @param taskRepoDO
     * @return
     */
    public Task buildTask(SimpleTaskRepoDO taskRepoDO) {
        if (taskRepoDO == null) {
            return null;
        }
        TaskType taskType = TaskType.valueOf(taskRepoDO.getType());
        if (taskType == null) {
            return null;
        }

        Class<? extends Task> taskClazz = taskType.getTaskClazz();
        if (taskClazz == null) {
            return null;
        }
        Task task = null;
        try {
            task = taskClazz.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            logger.error("创建任务实例失败", taskRepoDO, e);
        }

        if (task != null) {
            task.setTaskId(taskRepoDO.getId());
            String configStr = taskRepoDO.getConfig();
            JSONObject config = JSON.parseObject(configStr);
            task.restoreConfig(config);
            // 进行 spring 依赖注入
            factory.autowireBeanProperties(task, AutowireCapableBeanFactory.AUTOWIRE_BY_NAME, false);
        }
        return task;
    }
}
