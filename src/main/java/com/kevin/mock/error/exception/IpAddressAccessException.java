package com.kevin.mock.error.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @description: Ip地址访问异常
 * @author: kevinLiu
 * @date: 2021/8/9
 */
@Data
@AllArgsConstructor
public class IpAddressAccessException extends RuntimeException {
    private String ipAddress;
    private Integer count;

    public IpAddressAccessException(String ipAddress, String message) {
        super(message);
        this.ipAddress = ipAddress;
        count = -1;
    }
}
