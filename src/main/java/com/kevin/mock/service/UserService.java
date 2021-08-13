package com.kevin.mock.service;

import com.kevin.mock.dto.req.LoginReqDTO;
import com.kevin.mock.dto.req.RegisterReqDTO;
import com.kevin.mock.dto.req.UpdateMsgReqDTO;
import com.kevin.mock.dto.req.UpdatePwdReqDTO;
import com.kevin.mock.dto.common.result.ResponseResult;
import com.kevin.mock.dto.resp.GetUserInfoRespDTO;
import com.kevin.mock.dto.resp.LoginRespDTO;
import com.kevin.mock.dto.resp.RegisterRespDTO;

/**
 * @param: null
 * @description: User服务类
 * @author: kevinLiu
 * @date: 2021/8/6
 * @return:
 */
public interface UserService {

    /**
     * @param: registerVO
     * @description: 注册用户
     * @author: kevinLiu
     * @date: 2021/8/12
     * @return: com.kevin.mock.dto.common.result.ResponseResult<com.kevin.mock.dto.resp.RegisterRespDTO>
     */
    ResponseResult<RegisterRespDTO> registerUser(RegisterReqDTO registerVO);

    /**
     *  用户登录
     * @param: loginDTO DTO
     * @author: kevinLiu
     * @date: 2021/8/12
     * @return: com.kevin.mock.dto.common.result.ResponseResult<com.kevin.mock.dto.resp.LoginRespDTO>
     */
    ResponseResult<LoginRespDTO> userLogin(LoginReqDTO loginDTO);

    /**
     * @param: credentials
     * @description: 获取用户信息
     * @author: kevinLiu
     * @date: 2021/8/12
     * @return: com.kevin.mock.dto.common.result.ResponseResult<com.kevin.mock.dto.resp.GetUserInfoRespDTO>
     */
    ResponseResult<GetUserInfoRespDTO> getUserInfo(String credentials);

    /**
     * @param: credentials
     * @description: 用户登出
     * @author: kevinLiu
     * @date: 2021/8/12
     * @return: com.kevin.mock.dto.common.result.ResponseResult
     */
    ResponseResult userLogout(String credentials);

    /**
     * @param: credentials
     * @param: updatePasswordDTO
     * @description: 更新密码
     * @author: kevinLiu
     * @date: 2021/8/12
     * @return: com.kevin.mock.dto.common.result.ResponseResult
     */
    ResponseResult updatePassword(String credentials, UpdatePwdReqDTO updatePasswordDTO);

    /**
     * @param: credentials
     * @param: updateMsgRequestDTO
     * @description: 更新用户信息
     * @author: kevinLiu
     * @date: 2021/8/12
     * @return: com.kevin.mock.dto.common.result.ResponseResult
     */
    ResponseResult updateUserInfo(String credentials, UpdateMsgReqDTO updateMsgRequestDTO);
}
