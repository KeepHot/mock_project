package com.kevin.mock.service.helper.redis.impl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author kevinliu
 * @projectName Mock
 * @ClassName UserTokenKey.java
 * @Description 用户Token的 Key 设计
 * @createTime 2021年07月29日 11:05:00
 */
@Data
@Builder
@AllArgsConstructor
public class UserTokenKeyPrefix extends AbstractKeyPrefix {
    /**
     * @description: serToken的默认过期时间为10分钟
     * @author: kevinLiu
     * @date: 2021/8/5
     */
    private static final Integer TOKEN_EXPIRE_SECONDS = 60*10;
    private static final String  TOKEN_PREFIX = "user:userid:";

    public UserTokenKeyPrefix(Integer userId) {
        super(TOKEN_EXPIRE_SECONDS, TOKEN_PREFIX+userId);
    }
}



