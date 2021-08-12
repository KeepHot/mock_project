package com.kevin.mock.dto.resp;

import com.kevin.mock.dao.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
    /**
     * @description: 用户
     */
    private User user;
}
