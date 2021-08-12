package com.kevin.mock.service.helper.redis.impl;

import com.kevin.mock.service.helper.redis.KeyPrefix;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kevinliu
 * @projectName Mock
 * @ClassName BasePrefix.java
 * @Description Key的抽象类设计，所有的Key都继承自它
 * @createTime 2021年07月29日 10:38:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractKeyPrefix implements KeyPrefix {
    private Integer expireSeconds;
    private String prefix;

    /**
     * @param:
     * @description: 获取过期时间
     * @author: kevinLiu
     * @date: 2021/7/29
     * @return: java.lang.Integer
     */
    @Override
    public Integer getExpireSeconds(){
        return expireSeconds;
    }

}
