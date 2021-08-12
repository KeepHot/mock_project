package com.kevin.mock.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @description: 对系统内部错误的类别进行枚举
 * @author: kevinLiu
 * @date: 2021/8/5
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ErrorCode {

    //服务器错误
    SERVER_SUCCESS            (0,"Server success"),
    SERVER_TIMEOUT            (-10001,"Server timeout"),
    //限流手段生效
    SERVER_BUSY               (-10002,"Server busy"),
    SERVER_INTERNAL_ERROR     (-10003,"Server internal error"),
    SYSTEM_PARAMETER_ERROR    (-10004,"Parameter check error"),
    SYSTEM_NONSUPPORT_MEDIA   (-10005,"Not support media type"),

    //用户注册错误
    EMAIL_IS_EMPTY            (-20001,"Password is not valid"),
    EMAIL_NOT_LEGAL           (-20002,"Email is not legal"),
    EMAIL_OUT_LENGTH          (-20002,"Email is through long"),
    PASSWORD_NOT_LEGAL        (-20003,"Password is not legal"),
    PASSWORD_IS_EMPTY         (-20004,"Password is not valid"),
    EMAIL_HAS_REGISTERED      (-20005,"Email is registered"),

    //用户登录错误
    PASSWORD_WRONG            (-20011,"Password is wrong"),
    EMAIL_NOT_EXIST           (-20012,"Email is not exist"),

    //凭证信息失效
    CREDENTIALS_ILLEGAL       (-20021,"Credentials illegal"),
    CREDENTIALS_NOT_MATCH     (-20022,"Credentials invalid"),
    CREDENTIALS_IS_EXPIRED    (-20023,"Credential expire"),

    //更新用户信息
    NICKNAME_IS_EMPTY         (-20031,"Nickname is not legal"),
    ADDRESS_IS_EMPTY          (-20032,"Address is not legal"),
    NICKNAME_TOO_LONG         (-20031,"Nickname is too long"),
    ADDRESS_TOO_LONG          (-20032,"Address is too long"),

    //修改密码
    OLD_PASSWORD_NOT_LEGAL    (-20041,"Old password is not legal"),
    NEW_PASSWORD_NOT_LEGAL    (-20042,"New password is not legal"),
    OLD_PASSWORD_EMPTY        (-20044,"Old password is empty"),
    NEW_PASSWORD_EMPTY        (-20045,"New password is empty"),
    OLD_PASSWORD_IS_WRONG     (-20043,"Old password is wrong"),

    //短信服务
    MESSAGE_SEND_SUCCESS      (-20051,"Message send success"),
    MESSAGE_SEND_ERROR        (-20052,"Message send fail");

    private Integer code;
    private String msg;
}

