package com.kevin.mock.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;


import java.io.Serializable;

import static com.kevin.mock.constant.ConstantField.*;

/**
 * @description: 更新密码的请求DTO
 * @author: kevinLiu
 * @date: 2021/8/10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePwdReqDTO implements Serializable {


    private static final long serialVersionUID = -2705624129915218602L;
    /**
     * @description: 用户Id
     */
    private String userId;

    /**
     * @description: 旧密码
     */
    @NotEmpty(message = UPDATE_OLD_PWD_EMPTY_MESSAGE)
    @Pattern(regexp = "^(?![\\d]+$)(?![a-zA-Z]+$)(?![^\\da-zA-Z]+$).{6,20}$", message = UPDATE_OLD_PWD_ILLEGAL_MESSAGE)
    private String oldPassword;

    /**
     * @description: 新密码
     */
    @NotEmpty(message = UPDATE_NEW_PWD_EMPTY_MESSAGE)
    @Pattern(regexp = "^(?![\\d]+$)(?![a-zA-Z]+$)(?![^\\da-zA-Z]+$).{6,20}$", message = UPDATE_NEW_PWD_ILLEGAL_MESSAGE)
    private String newPassword;

}
