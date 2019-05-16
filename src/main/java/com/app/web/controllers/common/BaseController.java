package com.app.web.controllers.common;

import com.app.common.util.web.controllers.common.PageData;
import com.app.common.util.web.controllers.common.Result;
import com.app.core.model.ServiceResponse;
import com.iwallet.biz.common.util.PageList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Controller 接口父类.
 * <p>
 * 实现从 ServiceResponse 到 Result 的转换
 *
 * @author hzhang7
 */
public class BaseController {

    public static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    public static final String API_PREFIX = "/api";

    /**
     * 将service层列表数据转换为result结果数据
     *
     * @param response
     * @param <T>
     * @return
     */
    protected <T> Result<PageData<T>> convertPageListResponseToResult(ServiceResponse<PageList> response) {
        if (response.isSuccess()) {
            PageList pageList = response.getData();
            PageData<T> data = new PageData<>(pageList);
            return new Result<>(true, data, null);
        } else {
            return new Result<>(false, null, response.getError(), response.getErrorCode());
        }
    }

    /**
     * 将service层数据转换为result结果数据
     *
     * @param response
     * @param <T>
     * @return
     */
    protected <T> Result<T> convertResponseToResult(ServiceResponse<T> response) {
        return new Result<>(response.isSuccess(), response.getData(), response.getError(), response.getErrorCode());
    }

    /**
     * 完成对异常数据的处理
     *
     * @param request
     * @param response
     * @param exception
     * @return
     */
    @ResponseBody
    @ExceptionHandler
    public Result<Void> handleException(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        logger.error(request.getRequestURI(), exception);
        return new Result<>(false, null, exception.getMessage());
    }
}
