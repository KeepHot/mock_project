package com.kevin.mock.service;

import com.kevin.mock.service.helper.redis.impl.UserTokenKeyPrefix;

import java.util.concurrent.TimeUnit;

/**
 * @author liuquan
 */
public interface RedisService {

    boolean queryCertificate(String string);

    void deleteSpecificToken(String credentials);

    boolean isExpiredCertificate(String credentials);

    boolean queryBlackList(String ipAddress);

    void putBlackList(String ipAddress);

    Long tryGetBeginTime(String prefix, String fixedStr);

    Integer tryGetAccessCount(String prefix, String fixedStr);

    void expire(String fixedStr, long expireSeconds, TimeUnit seconds);

    void putIpAddressCurTime(String fixedStr, String limitingAccessCount, Long i);

    void putIpAddressCurCount(String fixedStr, String limitingAccessCount, int i);

    void operateLua(String cer);
}
