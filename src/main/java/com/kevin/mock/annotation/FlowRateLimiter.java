package com.kevin.mock.annotation;

import java.lang.annotation.*;

/**
 * @description: 针对高并发情况下的限流注解
 * @author: kevinLiu
 * @date: 2021/8/11
 */
@Target(ElementType.METHOD)//为方法注解
@Retention(RetentionPolicy.RUNTIME)//运行时生效
@Documented//自定义注解的时候，可以生成doc
public @interface FlowRateLimiter {

    /**
     * 默认的message
     * @author: kevinLiu
     * @date: 2021/8/3
     */
    String message() default  "服务器繁忙，请稍后重试";;

}
