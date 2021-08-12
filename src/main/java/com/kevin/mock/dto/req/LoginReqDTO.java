package com.kevin.mock.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import java.io.Serializable;

import static com.kevin.mock.constant.ConstantField.*;
import static com.kevin.mock.constant.ConstantField.REGISTER_PWD_ILLEGAL_MESSAGE;

/**
 * @description: 请求的登录DTO
 * @author: kevinLiu
 * @date: 2021/8/6
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginReqDTO implements Serializable {
    /**
     * @description: 序列化版本号
     */
    private static final long serialVersionUID = 3874125633784481103L;

    /**
     * @description: 邮箱
     */
    @Length(min = 1, max = 100, message = REGISTER_EMAIL_THROUGH_LONG)
    @NotEmpty(message = REGISTER_EMAIL_EMPTY_MESSAGE)
    @Pattern(regexp = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$", message = REGISTER_EMAIL_ILLEGAL_MESSAGE)
    private String email;


    /**
     * @description: 密码
     */
    @NotEmpty(message = REGISTER_PWD_EMPTY_MESSAGE)
    @Pattern(regexp = "^(?![\\d]+$)(?![a-zA-Z]+$)(?![^\\da-zA-Z]+$).{6,20}$", message = REGISTER_PWD_ILLEGAL_MESSAGE)
    private String password;
}
