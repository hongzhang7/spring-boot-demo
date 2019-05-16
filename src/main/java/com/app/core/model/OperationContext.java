package com.app.core.model;

import com.app.core.model.vo.UserVO;

/**
 * 当前访问的操作环境
 *
 * 对于增、删、改场景，默认 Service 接口中的每一个方法都应该包含 OperationContext 参数
 * 对于需要进行数据校验的场景也需要包含 OperationContext 参数
 */
public class OperationContext {

    /**
     * 当前操作用户
     */
    private UserVO operator;

    public UserVO getOperator() {
        return operator;
    }

    public void setOperator(UserVO operator) {
        this.operator = operator;
    }
}
