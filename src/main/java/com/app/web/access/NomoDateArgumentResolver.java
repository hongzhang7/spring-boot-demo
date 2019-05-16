package com.app.web.access;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.text.SimpleDateFormat;

/**
 * Mapping 分页参数信息提取
 *
 * @author hzhang7
 */
@Component
public class NomoDateArgumentResolver implements HandlerMethodArgumentResolver {


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(DateTimeFormat.class);
    }

    @Nullable
    @Override
    public Object resolveArgument(MethodParameter methodParameter, @Nullable ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {
        if(methodParameter.hasParameterAnnotation(DateTimeFormat.class)) {
            String dateStr = webRequest.getParameter(methodParameter.getParameterName());
            if(StringUtils.isEmpty(dateStr)) {
                return null;
            }
            DateTimeFormat format = methodParameter.getParameterAnnotation(DateTimeFormat.class);
            SimpleDateFormat dateFormat = new SimpleDateFormat(format.pattern());

            return dateFormat.parse(dateStr);
        }
        return null;
    }
}
