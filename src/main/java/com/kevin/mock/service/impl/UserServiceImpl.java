package com.kevin.mock.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.kevin.mock.dao.mapper.UserMapper;
import com.kevin.mock.dto.req.LoginReqDTO;
import com.kevin.mock.dto.req.RegisterReqDTO;
import com.kevin.mock.dto.req.UpdateMsgReqDTO;
import com.kevin.mock.dto.req.UpdatePwdReqDTO;
import com.kevin.mock.dao.model.User;
import com.kevin.mock.dto.resp.GetUserInfoRespDTO;
import com.kevin.mock.dto.resp.LoginRespDTO;
import com.kevin.mock.dto.resp.RegisterRespDTO;
import com.kevin.mock.error.exception.BizException;
import com.kevin.mock.service.helper.redis.impl.UserTokenKeyPrefix;
import com.kevin.mock.dto.common.result.ResponseResult;
import com.kevin.mock.service.RedisService;
import com.kevin.mock.service.UserService;
import com.kevin.mock.util.CertificateUtil;
import com.kevin.mock.util.Md5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static com.kevin.mock.constant.ErrorCode.*;
import static com.kevin.mock.dto.common.result.ResponseResult.isSuccess;

/**
 * @description: 用户服务具体实现类
 * @author: kevinLiu
 * @date: 2021/7/23
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userDao;
    private final RedisService redisService;

    public UserServiceImpl(UserMapper userDao, RedisService redisService) {
        this.redisService = redisService;
        this.userDao = userDao;
    }


    /**
     * @param: registerDTO
     * @description: 注册用户
     * @author: kevinLiu
     * @date: 2021/8/2
     */
    @Override
    public ResponseResult<RegisterRespDTO> registerUser(RegisterReqDTO registerReqDTO) {
        String email = registerReqDTO.getEmail();
        String passSalt = Md5Util.getNewSalt();
        String dbPassword = Md5Util.string2Md5(registerReqDTO.getPassword(), passSalt);
        LocalDateTime localDateTime = LocalDateTime.now();
        //builder创建新注册对象
        User registerUser = User.builder().email(email).pwdSalt(passSalt)
                .password(dbPassword).createAt(localDateTime).updateAt(localDateTime).build();
        //插入数据的异常处理
        try {
            userDao.registerUser(registerUser);
            //时区转换
            String zoneTime = localDateTime.atZone(ZoneOffset.UTC).toString();
            RegisterRespDTO respDTO = RegisterRespDTO.builder().createAt(zoneTime).
                    userId(registerUser.getUserId()).build();
            return new ResponseResult<>(SERVER_SUCCESS, respDTO);
        } catch (Exception exception) {
            if (exception instanceof DuplicateKeyException) {
                throw new BizException("Email has registered,please try another email", exception);
            } else {
                throw exception;
            }
        }
    }

    /**
     * @param: loginDTO
     * @description: 用户登录
     * @author: kevinLiu
     * @date: 2021/8/2
     */
    @Override
    public ResponseResult<LoginRespDTO> userLogin(LoginReqDTO loginDTO) {
        //根据邮箱判断用户是否存在
        User loginUser = userDao.queryUserByEmail(loginDTO.getEmail());
        if (null == loginUser) {
            return ResponseResult.error(EMAIL_NOT_EXIST);
        }
        //校验密码是否正确
        String salt = loginUser.getPwdSalt();
        String parameterPwd = Md5Util.string2Md5(loginDTO.getPassword(), salt);
        if (!parameterPwd.equals(loginUser.getPassword())) {
            return ResponseResult.error(PASSWORD_WRONG);
        }
        //生成凭证
        String cerStr = CertificateUtil.createNewCertification(loginUser.getUserId());
        UserTokenKeyPrefix tokenKey = new UserTokenKeyPrefix(loginUser.getUserId());
        //执行lua脚本，以保持原子性
        redisService.operateLua(cerStr);
        LoginRespDTO respDTO = LoginRespDTO.builder().token(cerStr).
                email(loginUser.getEmail()).
                address(loginUser.getAddress()).
                nickname(loginUser.getNickname()).
                userId(String.valueOf(loginUser.getUserId())).
                expiresIn(tokenKey.getExpireSeconds().toString()).
                updateAt(loginUser.getUpdateAt().atZone(ZoneOffset.UTC).toString()).
                createAt(loginUser.getCreateAt().atZone(ZoneOffset.UTC).toString()).build();
        return new ResponseResult<>(SERVER_SUCCESS, respDTO);
    }

    /**
     * @param: certificate
     * @description: 用户登出
     * @author: kevinLiu
     * @date: 2021/8/4
     */
    @Override
    public ResponseResult<JSONObject> userLogout(String certificate) {
        ResponseResult<JSONObject> tokenVerifyRes = tokenVerify(certificate);
        if (isSuccess(tokenVerifyRes)) {
            return tokenVerifyRes;
        }
        //直接试图从redis删除凭证
        redisService.deleteSpecificToken(certificate);
        return new ResponseResult(SERVER_SUCCESS, new JSONObject());
    }

    /**
     * @param: certificate
     * @description: 获取用户信息
     * @author: kevinLiu
     * @date: 2021/8/4
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseResult<GetUserInfoRespDTO> getUserInfo(String certificate) {
        ResponseResult tokenVerifyRes = tokenVerify(certificate);
        if (isSuccess(tokenVerifyRes)) {
            return tokenVerifyRes;
        }
        String userId = CertificateUtil.getUserIdByCer(certificate);
        User queryUser = userDao.queryUserByUserId(userId);
        GetUserInfoRespDTO respDTO = GetUserInfoRespDTO.builder().
                userId(String.valueOf(queryUser.getUserId())).
                email(queryUser.getEmail()).
                nickname(queryUser.getNickname()).
                address(queryUser.getAddress()).
                createAt(queryUser.getCreateAt().atZone(ZoneOffset.UTC).toString()).
                updateAt(queryUser.getUpdateAt().atZone(ZoneOffset.UTC).toString()).build();
        return new ResponseResult<>(SERVER_SUCCESS, respDTO);
    }

    /**
     * @param: certificate
     * @param: updateMsgDTO
     * @description: 更新用户信息
     * @author: kevinLiu
     * @date: 2021/8/4
     */
    @Override
    public ResponseResult<JSONObject> updateUserInfo(String certificate, UpdateMsgReqDTO updateMsgReqDTO) {
        ResponseResult<JSONObject> tokenVerifyRes = tokenVerify(certificate);
        if (isSuccess(tokenVerifyRes)) {
            return tokenVerifyRes;
        }
        updateMsgReqDTO.setUserId(CertificateUtil.getUserIdByCer(certificate));
        User updateUser = new User(updateMsgReqDTO);
        userDao.updateUserById(updateUser);
        return new ResponseResult<>(SERVER_SUCCESS, new JSONObject());
    }

    /**
     * 更新用户密码
     *
     * @throws
     * @author: kevinLiu
     * @date: 2021/8/12
     * @return:
     */
    @Override
    public ResponseResult<JSONObject> updatePassword(String certificate, UpdatePwdReqDTO updatePwdReqDTO) {
        //Token的校验
        ResponseResult<JSONObject> tokenVerifyRes = tokenVerify(certificate);
        if (isSuccess(tokenVerifyRes)) {
            return tokenVerifyRes;
        }
        //判断旧密码是否正确
        String userId = CertificateUtil.getUserIdByCer(certificate);
        User queryUser = userDao.queryUserByUserId(userId);
        if (queryUser == null) {
            return ResponseResult.error(SERVER_INTERNAL_ERROR);
        }
        String pwdSalt = queryUser.getPwdSalt();
        if (!queryUser.getPassword().equals(
                Md5Util.string2Md5(updatePwdReqDTO.getOldPassword(), pwdSalt))) {
            return ResponseResult.error(OLD_PASSWORD_IS_WRONG);
        }
        //封装为对象传入
        updatePwdReqDTO.setUserId(userId);
        updatePwdReqDTO.setNewPassword(Md5Util.string2Md5(updatePwdReqDTO.getNewPassword(), pwdSalt));
        User updateUser = new User(updatePwdReqDTO);
        userDao.updatePasswordById(updateUser);
        return new ResponseResult<>(SERVER_SUCCESS, new JSONObject());
    }

    /**
     * @param: certificate
     * @description: Token子流程的校验
     * @author: kevinLiu
     * @date: 2021/8/4
     */
    private ResponseResult<JSONObject> tokenVerify(String certificate) {
        boolean isLegal = CertificateUtil.isLegalCertification(certificate);
        if (!isLegal) {
            return new ResponseResult<>(CREDENTIALS_ILLEGAL, new JSONObject());
        }
        //用户凭证是否存在
        if (!redisService.queryCertificate(certificate)) {
            return new ResponseResult<>(CREDENTIALS_NOT_MATCH, new JSONObject());
        }
        //凭证是否过期
        if (redisService.isExpiredCertificate(certificate)) {
            return new ResponseResult<>(CREDENTIALS_IS_EXPIRED, new JSONObject());
        }
        return new ResponseResult<>(SERVER_SUCCESS, new JSONObject());
    }
}
