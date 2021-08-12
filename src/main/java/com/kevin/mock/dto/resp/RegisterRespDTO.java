package com.kevin.mock.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * @description: 注册返回的DTO
 * @author: kevinLiu
 * @date: 2021/8/10
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRespDTO implements Serializable {

    private static final long serialVersionUID = 3059159688484893137L;
    /**
     * @description: userId
     */
    private String userId;
    /**
     * @description: 创建时间
     */
    private String createAt;
}
