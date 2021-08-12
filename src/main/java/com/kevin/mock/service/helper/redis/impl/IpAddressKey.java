package com.kevin.mock.service.helper.redis.impl;

/**
 * @author kevinliu
 * @projectName Mock
 * @ClassName IPAddressKey.java
 * @Description IP地址的Key设计
 * @createTime 2021年07月29日 14:01:00
 */
public class IpAddressKey extends AbstractKeyPrefix {
    /**
     * @description: IP地址默认是过期时间为5分钟
     */
    private static final Integer IP_ADDRESS_COUNT = 10;
    public IpAddressKey(String ipAddress, Integer integer){
        super(integer, ipAddress);
    }
    public static IpAddressKey IPAccessKey = new IpAddressKey(
            "IPAddress",IP_ADDRESS_COUNT);
}
