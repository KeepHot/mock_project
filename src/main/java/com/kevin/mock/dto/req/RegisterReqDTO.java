package com.kevin.mock.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import java.io.Serializable;

import static com.kevin.mock.constant.ConstantField.*;

/**
 * @description: 用户注册的DTO请求对象
 * @author: kevinLiu
 * @date: 2021/8/10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterReqDTO implements Serializable {

    private static final long serialVersionUID = 1446962093482435762L;
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
