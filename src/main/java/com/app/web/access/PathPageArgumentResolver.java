package com.app.web.access;

import com.app.common.util.web.controllers.common.PageConfig;
import com.app.web.annotations.PathPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Mapping 用户分页信息参数提取
 *
 * @author xunwu.zy
 */
@Component
public class PathPageArgumentResolver implements HandlerMethodArgumentResolver {

    public static final String PAGE_SIZE_PARAMETER = "pageSize";

    public static final String PAGE_NUM_PARAMETER = "pageNum";


    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(PathPage.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {

        if (methodParameter.hasParameterAnnotation(PathPage.class)) {
            String pageNumStr = nativeWebRequest.getParameter(PAGE_NUM_PARAMETER);
            String pageSizeStr = nativeWebRequest.getParameter(PAGE_SIZE_PARAMETER);

            int pageSize = PageConfig.DEFAULT_PAGE_SIZE;
            if(StringUtils.isNumeric(pageSizeStr)) {
                pageSize = Math.max(1, Integer.parseInt(pageSizeStr));
            }

            int pageNum = PageConfig.DEFAULT_PAGE_NUM;
            if(StringUtils.isNumeric(pageNumStr)) {
                pageNum = Math.max(1, Integer.parseInt(pageNumStr));
            }

            return new PageConfig(pageSize, pageNum);
        }

        return null;
    }
}
