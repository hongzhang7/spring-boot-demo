package com.app.web.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使用在 Mapping 方法中，用于对分页字段进行提取
 *
 * @author xunwu.zy
 * @see com.app.web.access.PathPageArgumentResolver
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PathPage {
}
