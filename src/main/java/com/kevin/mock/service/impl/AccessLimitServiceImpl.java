package com.kevin.mock.service.impl;

import com.google.common.util.concurrent.RateLimiter;
import com.kevin.mock.service.AccessLimitService;
import org.springframework.stereotype.Service;

/**
 * RateLimiter限流工具服务类
 * @author KevinLiu
 */
@Service
public class AccessLimitServiceImpl implements AccessLimitService {

    RateLimiter rateLimiter = RateLimiter.create(5.0);

    /**
     * 尝试去获取令牌
     * @return
     */
    @Override
    public boolean tryAcquire() {
        return rateLimiter.tryAcquire();
    }
}
