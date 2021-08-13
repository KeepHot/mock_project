package com.kevin.mock.error.exception;

/**
 * @description: 并发限流异常
 * @author: kevinLiu
 * @date: 2021/8/10
 */
public class RateLimiterException extends RuntimeException{
    public RateLimiterException(String message){
        super(message);
    }
}
