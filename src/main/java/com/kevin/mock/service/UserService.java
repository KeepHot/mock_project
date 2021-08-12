package com.kevin.mock.service;

import com.kevin.mock.dto.req.LoginReqDTO;
import com.kevin.mock.dto.req.RegisterReqDTO;
import com.kevin.mock.dto.req.UpdateMsgReqDTO;
import com.kevin.mock.dto.req.UpdatePwdReqDTO;
import com.kevin.mock.dto.common.result.ResponseResult;
import com.kevin.mock.dto.resp.RegisterRespDTO;

/**
 * @param: null
 * @description: User服务类
 * @author: kevinLiu
 * @date: 2021/8/6
 * @return:
 */
public interface UserService {

    ResponseResult<RegisterRespDTO> registerUser(RegisterReqDTO registerVO);

    ResponseResult userLogin(LoginReqDTO loginDTO);

    ResponseResult userLogout(String credentials);

    ResponseResult getUserInfo(String credentials);

    ResponseResult updatePassword(String credentials, UpdatePwdReqDTO updatePasswordDTO);

    ResponseResult updateUserInfo(String credentials, UpdateMsgReqDTO updateMsgRequestDTO);
}
