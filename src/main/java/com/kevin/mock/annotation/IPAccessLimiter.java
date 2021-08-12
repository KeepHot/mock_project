package com.kevin.mock.annotation;

import java.lang.annotation.*;

/**
 * @description: IP地址防刷限制注解
 * @author: kevinLiu
 * @date: 2021/8/10
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IPAccessLimiter {

    /**
     * 周期内最大访问次数,默认为 5 次
     * @author: kevinLiu
     * @date: 2021/8/3
     */
    int maxAccessCount() default  5;

    /**
     * 固定周期时间，单位：mS（因为ms作为系统时间的单位） 默认为 1 分钟
     * @author: kevinLiu
     * @date: 2021/8/3
     */
    long fixedCycleMilliSecond() default 60 * 1000;

    /**
     * 禁止访问时间 单位：s 默认1分钟 在周期时间超过了指定的次数，则默认一分钟无法访问
     * @author: kevinLiu
     * @date: 2021/8/3
     */
    long expireSeconds() default 60;

    /**
     * @description: 信息描述
     * @author: kevinLiu
     * @date: 2021/8/3
     */
    String messages() default "请求过于频繁，本次请求已经被拒绝";
}
