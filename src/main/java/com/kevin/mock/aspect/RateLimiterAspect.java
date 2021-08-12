package com.kevin.mock.aspect;

import com.kevin.mock.annotation.FlowRateLimiter;
import com.kevin.mock.error.exception.RateLimiterException;
import com.kevin.mock.service.AccessLimitService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @description: 高并发的限流注解
 * @author: kevinLiu
 * @date: 2021/8/10
 */
@Slf4j
@Aspect
@Component
public class RateLimiterAspect {

    private final AccessLimitService limitService;

    public RateLimiterAspect(AccessLimitService limitService){
        this.limitService = limitService;
    }

    @Pointcut("@annotation(flowRateLimiter)")
    public void pointCut(FlowRateLimiter flowRateLimiter) {

    }

    @Around(value = "pointCut(flowRateLimiter)", argNames = "pjp,flowRateLimiter")
    public Object around(ProceedingJoinPoint pjp, FlowRateLimiter flowRateLimiter) throws Throwable {

        if (limitService.tryAcquire()) {
            return pjp.proceed();
        } else {
            throw new RateLimiterException(flowRateLimiter.message());
        }
    }
}
