package com.kevin.mock.constant;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @description: 路径映射常量
 * @author: kevinLiu
 * @date: 2021/8/5
 */
public class ConstantField {

    /**
     * @description: message的K—V信息
     * @author: kevinLiu
     * @date: 2021/8/10
     */
    public static Map<String, ErrorCode> messageMap = new HashMap<>();

    /**
     * Http协议的 X_AUTHORIZATION 请求头
     */
    public static final String X_AUTHORIZATION = "x_authorization";
    /**
     * Http协议的content类型
     */
    public static final String HTTP_MEDIA_TYPE = "application/json";

    /**
     * Token的后半部分的长度
     */
    public static final int TOKEN_LENGTH = 20;

    /**
     * Token的最大个数限制
     */
    public static final String TOKEN_MAX_NUMBER = "5";

    /**
     * 正则表达式
     */
    public static final Pattern FIGURE_PATTERN = Pattern.compile("[0-9]*");

    /**
     * Redis 过期时间设置
     */
    public static final int REDIS_EXPIRE_MILLISECOND = 300000;
    /**
     * Controller 映射总路径
     */
    public static final String MAP_TOTAL_PATH = "/api/v1/user";

    /**
     * Controller 注册映射路径
     */
    public static final String MAP_REGISTER_PATH = "/register";

    /**
     * Controller 登录映射路径
     */
    public static final String MAP_LOGIN_PATH = "/login";

    /**
     * Controller 登出映射路径
     */
    public static final String MAP_LOGOUT_PATH = "/logout";

    /**
     * Controller 获取映射路径
     */
    public static final String MAP_GET_MSG_PATH = "/getUserInfo";

    /**
     * Controller 更新密码路径
     */
    public static final String MAP_UPDATE_PWD_PATH = "/updatePassword";


    /**
     * Controller 更新信息路径
     */
    public static final String MAP_UPDATE_MSG_PATH = "/updateUserInfo";

    /**
     * RegisterReqDTO中邮箱的默认空信息
     */
    public static final String REGISTER_EMAIL_EMPTY_MESSAGE = "Email is empty";

    /**
     * RegisterReqDTO中邮箱的默认空信息
     */
    public static final String REGISTER_PWD_EMPTY_MESSAGE = "Password is empty";

    /**
     * RegisterReqDTO中邮箱的默认不合法信息
     */
    public static final String REGISTER_EMAIL_THROUGH_LONG = "Email is through the long";

    /**
     * nickname 长度
     */
    public static final String NICKNAME_THROUGH_LONG = "Nickname is through the long";

    /**
     * address长度问题
     */
    public static final String ADDRESS_THROUGH_LONG = "Address is through the long";

    /**
     * RegisterReqDTO中邮箱的默认不合法信息
     */
    public static final String REGISTER_EMAIL_ILLEGAL_MESSAGE = "Email is illegal";

    /**
     * RegisterReqDTO中邮箱的默认不合法信息
     */
    public static final String REGISTER_PWD_ILLEGAL_MESSAGE = "Password is illegal";

    /**
     * UpdateMsgReqDTO中nickname的默认空信息
     */
    public static final String UPDATE_MSG_EMPTY_MESSAGE = "UpdateMSG is empty";

    /**
     * UpdateMsgReqDTO中 address 的默认空信息
     */
    public static final String UPDATE_ADDRESS_EMPTY_MESSAGE = "UpdateAddress is empty";

    /**
     * UpdatePwdReqDTO 旧密码默认空信息
     */
    public static final String UPDATE_OLD_PWD_EMPTY_MESSAGE = "Update Old PWD is empty";

    /**
     * UpdatePwdReqDTO 新密码默认空信息
     */
    public static final String UPDATE_NEW_PWD_EMPTY_MESSAGE = "Update New PWD is empty";

    /**
     * UpdatePwdReqDTO 中旧密码的默认不合法信息
     */
    public static final String UPDATE_OLD_PWD_ILLEGAL_MESSAGE = "Old password is illegal";
    /**
     * UpdatePwdReqDTO 中新密码的默认不合法信息
     */
    public static final String UPDATE_NEW_PWD_ILLEGAL_MESSAGE = "New password is illegal";

    static {
        messageMap.put(REGISTER_EMAIL_THROUGH_LONG, ErrorCode.EMAIL_OUT_LENGTH);
        messageMap.put(REGISTER_EMAIL_ILLEGAL_MESSAGE, ErrorCode.EMAIL_NOT_LEGAL);
        messageMap.put(REGISTER_PWD_ILLEGAL_MESSAGE, ErrorCode.PASSWORD_NOT_LEGAL);
        messageMap.put(REGISTER_EMAIL_EMPTY_MESSAGE, ErrorCode.EMAIL_IS_EMPTY);
        messageMap.put(REGISTER_PWD_EMPTY_MESSAGE, ErrorCode.PASSWORD_IS_EMPTY);
        messageMap.put(NICKNAME_THROUGH_LONG, ErrorCode.NICKNAME_TOO_LONG);
        messageMap.put(ADDRESS_THROUGH_LONG, ErrorCode.ADDRESS_TOO_LONG);
        messageMap.put(UPDATE_MSG_EMPTY_MESSAGE, ErrorCode.NICKNAME_IS_EMPTY);
        messageMap.put(UPDATE_ADDRESS_EMPTY_MESSAGE, ErrorCode.ADDRESS_IS_EMPTY);
        messageMap.put(UPDATE_OLD_PWD_ILLEGAL_MESSAGE, ErrorCode.OLD_PASSWORD_NOT_LEGAL);
        messageMap.put(UPDATE_NEW_PWD_ILLEGAL_MESSAGE, ErrorCode.NEW_PASSWORD_NOT_LEGAL);
        messageMap.put(UPDATE_OLD_PWD_EMPTY_MESSAGE, ErrorCode.OLD_PASSWORD_EMPTY);
        messageMap.put(UPDATE_NEW_PWD_EMPTY_MESSAGE, ErrorCode.NEW_PASSWORD_EMPTY);
    }
}
