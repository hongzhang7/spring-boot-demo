package com.app.core.model;

import org.slf4j.Logger;

/**
 * 标准化 OperatorContext 上下文日志
 *
 * @author xunwu.zy
 */
public class ContextLogger {

    /**
     * 输出info信息
     *
     * @param logger
     * @param msg
     * @param context
     * @param objects
     */
    public static void info(Logger logger, String msg, OperationContext context, Object... objects) {
        if (logger.isInfoEnabled()) {
            logger.warn("{} with context ${}$ objects ${}$", msg, context, objects);
        }
    }

    /**
     * 输出 error 信息
     *
     * @param logger
     * @param msg
     * @param context
     * @param objects
     */
    public static void warn(Logger logger, String msg, OperationContext context, Object... objects) {
        logger.warn("{} with context ${}$ objects ${}$", msg, context, objects);
    }

    /**
     * 输出 error 信息
     *
     * @param logger
     * @param msg
     * @param context
     * @param objects
     */
    public static void error(Logger logger, String msg, OperationContext context, Object... objects) {
        logger.error("{} with context ${}$ objects ${}$", msg, context, objects);
    }
}
