package com.app.core.model;

import com.app.core.model.vo.UserVO;

/**
 * 当前访问的操作环境
 * <p>
 * 对于 增、删、改场景，默认 Facade 接口中的每一个方法都应该包含 OperationContext 参数
 * <p>
 * 对于需要进行数据操作校验的场景也需要包含 OperationContext 参数
 *
 * @author xunwu.zy
 */
public class OperationContextHolder {

    public static final ThreadLocal<OperationContext> currentOperationContext = new ThreadLocal<>();

    public static OperationContext get() {
        return currentOperationContext.get();
    }

    public static void set(OperationContext context) {
        currentOperationContext.set(context);
    }

    /**
     * 清理已存储的用户信息
     */
    public static void clear() {
        currentOperationContext.remove();
    }

    /**
     * 构造上下文对象
     *
     * @param userDAO
     * @param userId
     * @return
     */
//    public static OperationContext build(UserDAO userDAO, long userId) {
//        OperationContext context = new OperationContext();
//        UserDO userDO = userDAO.getUserById(userId);
//        context.setOperator(new UserVO(userDO));
//        return context;
//    }
}
