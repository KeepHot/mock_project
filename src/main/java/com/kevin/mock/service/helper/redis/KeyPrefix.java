package com.kevin.mock.service.helper.redis;

/**
 * @author kevinliu
 * @projectName Mock
 * @ClassName KeyPrefix.java
 * @Description Redis的前缀接口
 * @createTime 2021年07月29日 10:36:00
 */
public interface KeyPrefix {
    Integer getExpireSeconds();
    String getPrefix();
}
