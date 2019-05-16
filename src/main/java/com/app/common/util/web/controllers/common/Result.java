package com.app.common.util.web.controllers.common;

/**
 * 通过 Controller 返回的结果数据样式.
 *
 * @author xunwu.zy
 */
public class Result<T> {

    private final boolean success;
    private final T Data;
    private final String error;
    private final int errorCode;

    /**
     * 构造器
     *
     * @param success
     * @param data
     * @param error
     */
    public Result(boolean success, T data, String error) {
        this(success, data, error, 0);
    }

    /**
     * 构造器
     *
     * @param success
     * @param data
     * @param error
     * @param errorCode
     */
    public Result(boolean success, T data, String error, int errorCode) {
        this.success = success;
        Data = data;
        this.error = error;
        this.errorCode = errorCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return Data;
    }

    public String getError() {
        return error;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
