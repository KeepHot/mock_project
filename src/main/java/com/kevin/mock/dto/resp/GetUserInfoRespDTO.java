package com.kevin.mock.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description: 获取用户信息返回的DTO
 * @author: kevinLiu
 * @date: 2021/8/10
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetUserInfoRespDTO implements Serializable {


    private static final long serialVersionUID = 6882993308930782630L;

    private String userId;
    private String email;
    private String nickname;
    private String address;
    private String createAt;
    private String updateAt;
}
