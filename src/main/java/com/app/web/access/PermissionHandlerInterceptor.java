package com.app.web.access;

import com.app.web.annotations.ProjectPermission;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * 登陆、权限校验拦截器
 *
 * @author hzhang7
 */
@Component
public class PermissionHandlerInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(PermissionHandlerInterceptor.class);

    private final Map<Method, ProjectPermission> mMethodCache = new WeakHashMap<>();

    /**
    @Resource
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        OperationContext context = resetOperationContext(request, response);
        if(context == null) {
            LoginFailedException exception = new LoginFailedException("未登录");
            throw exception;
        }

        return true;
    }

    private OperationContext resetOperationContext(HttpServletRequest request, HttpServletResponse response) {
        OperationContextHolder.clear();
        OperationContext context = getOperationContext(request, response);
        OperationContextHolder.set(context);
        return context;
    }

    private OperationContext getOperationContext(HttpServletRequest request, HttpServletResponse response) {
        UserVO operator = null;

        try {
            String from = request.getHeader("device");
            if(StringUtils.isNotBlank(from) && from.equals("wechat")) {
                // 微信
                String userId = request.getHeader("userId");
                if(StringUtils.isNotBlank(userId)) {
                    ServiceResponse<UserVO> result = userService.queryUserInfoById(null, Long.parseLong(userId));
                    if(result.isSuccess()) {
                        OperationContext context = new OperationContext();
                        operator = result.getData();
                        String carLot = request.getParameter("carLot");
                        if (StringUtils.isNotBlank(carLot) && !"null".equals(carLot)) {
                            String catLotStr = request.getParameter("carLot");
                            CarLotEnum carLotEnum = CarLotEnum.valueOf(catLotStr);
                            operator.setCarLot(carLotEnum);
                        }
                        context.setOperator(operator);
                        context.setFlag(result.getData().getRole().getFlag());

                        return context;
                    }
                }
            } else {
                operator = BucUserUtils.getCurrentUser(request);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // pc
        if(operator != null && operator.getId() != null && operator.getId() > 0) {
            OperationContext context = new OperationContext();
            context.setOperator(operator);
            context.setFlag(operator.getRole().getFlag());

            // 更新cookie和session过期时间
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(30 * 60);
            session.removeAttribute(operator.getSessionId());
            session.setAttribute(operator.getSessionId(), operator);

            Cookie cookie = new Cookie("user_token", operator.getSessionId());
            cookie.setMaxAge(30 * 60);
            cookie.setPath("/");
            response.addCookie(cookie);
            return context;

        }
        return null;
    }
     */
}
