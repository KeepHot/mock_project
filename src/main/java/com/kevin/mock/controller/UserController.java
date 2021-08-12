package com.kevin.mock.controller;

import com.kevin.mock.annotation.IPAccessLimiter;
import com.kevin.mock.annotation.FlowRateLimiter;
import com.kevin.mock.dto.req.LoginReqDTO;
import com.kevin.mock.dto.req.RegisterReqDTO;
import com.kevin.mock.dto.req.UpdateMsgReqDTO;
import com.kevin.mock.dto.req.UpdatePwdReqDTO;
import com.kevin.mock.dto.common.result.ResponseResult;
import com.kevin.mock.dto.resp.RegisterRespDTO;
import com.kevin.mock.service.UserService;
import com.kevin.mock.util.HttpRequestUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.*;

import static com.kevin.mock.constant.ConstantField.*;

/**
 * @description: 用户User的控制层
 * @author: kevinLiu
 * @date: 2021/7/27
 */
@RestController
@RequestMapping(value = MAP_TOTAL_PATH)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * @param: registerReqDTO
     * @description: 用户注册
     * @author: kevinLiu
     * @date: 2021/8/6
     * @return: com.kevin.mock.dto.common.result.ResponseResult
     */
    @FlowRateLimiter
    @IPAccessLimiter
    @RequestMapping(value = MAP_REGISTER_PATH, method = RequestMethod.POST, consumes = "application/json")
    public ResponseResult<RegisterRespDTO> userRegister(@Validated RegisterReqDTO registerReqDTO) {
        return userService.registerUser(registerReqDTO);
    }

    /**
     * @param: loginReqDTO
     * @description: 用户登陆
     * @author: kevinLiu
     * @date: 2021/8/6
     * @return: com.kevin.mock.dto.common.result.ResponseResult
     */
    @FlowRateLimiter
    @IPAccessLimiter
    @RequestMapping(value = MAP_LOGIN_PATH, method = RequestMethod.POST, consumes = "application/json")
    public ResponseResult userLogin(@Validated LoginReqDTO loginReqDTO) {
        return userService.userLogin(loginReqDTO);
    }

    /**
     * @param: request
     * @description: 用户登出
     * @author: kevinLiu
     * @date: 2021/8/6
     * @return: com.kevin.mock.dto.common.result.ResponseResult
     */
    @FlowRateLimiter
    @IPAccessLimiter
    @RequestMapping(value = MAP_LOGOUT_PATH, method = RequestMethod.POST, consumes = "application/json")
    public ResponseResult userLogout(HttpServletRequest request) {
        String authorizationStr = HttpRequestUtil.getHeadersInfo(request).get(X_AUTHORIZATION);
        return userService.userLogout(authorizationStr);
    }

    /**
     * @param: request
     * @description: 获取用户信息
     * @author: kevinLiu
     * @date: 2021/8/6
     * @return: com.kevin.mock.dto.common.result.ResponseResult
     */
    @FlowRateLimiter
    @IPAccessLimiter
    @RequestMapping(value = MAP_GET_MSG_PATH, method = RequestMethod.POST, consumes = "application/json")
    public ResponseResult getUserInfo(HttpServletRequest request) {
        String authorizationStr = HttpRequestUtil.getHeadersInfo(request).get(X_AUTHORIZATION);
        return userService.getUserInfo(authorizationStr);
    }

    /**
     * @param: updateMsgReqDTO
     * @param: request
     * @description: 更新用户信息
     * @author: kevinLiu
     * @date: 2021/8/6
     * @return: com.kevin.mock.dto.common.result.ResponseResult
     */
    @FlowRateLimiter
    @IPAccessLimiter
    @RequestMapping(value = MAP_UPDATE_MSG_PATH, method = RequestMethod.POST, consumes = "application/json")
    public ResponseResult updateUserInfo(@Validated UpdateMsgReqDTO updateMsgReqDTO,
                                         HttpServletRequest request) {

        String authorizationStr = HttpRequestUtil.getHeadersInfo(request).get(X_AUTHORIZATION);
        return userService.updateUserInfo(authorizationStr, updateMsgReqDTO);
    }

    /**
     * @param: updatePwdReqDTO
     * @param: request
     * @description: 更新用户密码
     * @author: kevinLiu
     * @date: 2021/8/6
     * @return: com.kevin.mock.dto.common.result.ResponseResult
     */
    @FlowRateLimiter
    @IPAccessLimiter
    @RequestMapping(value = MAP_UPDATE_PWD_PATH, method = RequestMethod.POST, consumes = "application/json")
    public ResponseResult updatePassword( @Validated UpdatePwdReqDTO updatePwdReqDTO,
                                         HttpServletRequest request) {
        String authorizationStr = HttpRequestUtil.getHeadersInfo(request).get(X_AUTHORIZATION);
        return userService.updatePassword(authorizationStr, updatePwdReqDTO);

    }
}
