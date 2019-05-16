package com.app.core.model;

/**
 * 服务返回值数据.
 *
 * @author xunwu.zy
 */
public class ServiceResponse<D> {

    private final boolean success;

    private final D data;

    private final String error;

    private final int errorCode;

    /**
     * 构造器
     *
     * @param success
     * @param data
     * @param error
     */
    public ServiceResponse(boolean success, D data, String error) {
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
    public ServiceResponse(boolean success, D data, String error, int errorCode) {
        this.success = success;
        this.data = data;
        this.error = error;
        this.errorCode = errorCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public D getData() {
        return data;
    }

    public String getError() {
        return error;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
