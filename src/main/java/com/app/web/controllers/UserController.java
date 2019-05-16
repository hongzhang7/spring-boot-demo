package com.app.web.controllers;

import com.app.common.util.web.controllers.common.Result;
import com.app.core.model.OperationContext;
import com.app.core.model.vo.UserVO;
import com.app.web.controllers.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 用户相关操作接口
 *
 * @author wb-zh270168
 */
@Api(description = "用户相关接口")
@RestController
public class UserController extends BaseController {

    @ApiOperation("获取当前用户")
    @ResponseBody
    @RequestMapping(value = "/api/users/current", method = RequestMethod.GET, produces = "application/json")
    public Result<UserVO> currentUser(@ApiIgnore OperationContext context) {
        return new Result<>(true, context.getOperator(), null);
    }
}
