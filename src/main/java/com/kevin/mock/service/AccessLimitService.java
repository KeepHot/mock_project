package com.kevin.mock.service;

/**
 * @description: 限流服务
 * @author: kevinLiu
 * @date: 2021/8/10
 */
public interface AccessLimitService {
    /**
     * @description: 试图获取令牌
     * @author: kevinLiu
     * @date: 2021/8/10
     * @return: boolean
     */
    boolean tryAcquire();
}
