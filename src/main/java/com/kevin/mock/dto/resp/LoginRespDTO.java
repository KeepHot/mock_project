package com.kevin.mock.dto.resp;


import com.kevin.mock.dao.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description: 登录返回的DTO
 * @author: kevinLiu
 * @date: 2021/8/10
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRespDTO implements Serializable {

    private static final long serialVersionUID = 2723310985334541964L;
    /**
     * @description: token
     */
    private String token;
    /**
     * @description: token有效期
     */
    private String expiresIn;
    /**
     * @description: 用户
     */
    private User user;
}
