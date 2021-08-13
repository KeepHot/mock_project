package com.kevin.mock.service.impl;

import com.kevin.mock.service.RedisService;
import com.kevin.mock.util.CertificateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import static com.kevin.mock.constant.ConstantField.REDIS_EXPIRE_MILLISECOND;
import static com.kevin.mock.constant.ConstantField.TOKEN_MAX_NUMBER;

/**
 * @description: 封装的Redis操作类
 * @author: kevinLiu
 * @date: 2021/8/4
 */
@Slf4j
@Component
public class RedisServiceImpl implements RedisService {

    /**
     * @description: TOKEN_KEY
     */
    private static final String TOKEN_KEY = "user:userid:";
    /**
     * @description: 黑名单的Key
     */
    private static final String BLANK_LIST = "blacklist:ipaddress:";

    private final StringRedisTemplate redisTemplate;

    public RedisServiceImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private DefaultRedisScript<Number> addTokenScript;
    private DefaultRedisScript<Boolean> isExpiredScript;


    @PostConstruct
    public void initTokenLuaScript() {
        addTokenScript = new DefaultRedisScript<>();
        addTokenScript.setResultType(Number.class);
        addTokenScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("redis/SaveToken.lua")));
    }

    @PostConstruct
    public void initExpireLuaScript() {
        isExpiredScript = new DefaultRedisScript<>();
        isExpiredScript.setResultType(Boolean.class);
        isExpiredScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("redis/isExpired.lua")));
    }

    /**
     * @description: 查询是否存在指定的key
     * @author: kevinLiu
     * @date: 2021/8/3
     */
    @Override
    public boolean queryCertificate(String credentials) {
        return redisTemplate.opsForZSet().rank(TOKEN_KEY + CertificateUtil.getUserIdByCer(credentials),
                credentials) != null;
    }

    /**
     * @param: credentials
     * @description: 删除特定的token
     * @author: kevinLiu
     * @date: 2021/8/4
     */
    @Override
    public void deleteSpecificToken(String credentials) {
        redisTemplate.opsForZSet().remove(TOKEN_KEY + CertificateUtil.
                getUserIdByCer(credentials), credentials, System.currentTimeMillis());
    }

    /**
     * @param: credentials
     * @description: 判断元素是否过期
     * @author: kevinLiu
     * @date: 2021/8/4
     */
    @Override
    public boolean isExpiredCertificate(String cer) {
        String key = TOKEN_KEY + CertificateUtil.getUserIdByCer(cer);
        return redisTemplate.execute(isExpiredScript, Collections.singletonList(key), cer,
                String.valueOf(System.currentTimeMillis()));
    }

    /**
     * @param: ipAddress
     * @description: 把ip地址放入黑名单, 60s内禁止访问
     * @author: kevinLiu
     * @date: 2021/8/4
     */
    @Override
    public void putBlackList(String ipAddress) {
        redisTemplate.opsForValue().set(BLANK_LIST + ipAddress, ipAddress, 60, TimeUnit.SECONDS);
    }


    @Override
    public Long tryGetBeginTime(String prefix, String fixedStr) {
        String res = (String) redisTemplate.opsForHash().get(prefix, fixedStr);
        if (res != null) {
            return Long.valueOf(res).longValue();
        } else {
            return null;
        }
    }

    @Override
    public Integer tryGetAccessCount(String prefix, String fixedStr) {
        String res = (String) redisTemplate.opsForHash().get(
                prefix, fixedStr);
        if (res != null) {
            return Integer.valueOf(res).intValue();
        } else {
            return null;
        }
    }

    /**
     * @param: fixedStr
     * @param: expireSeconds
     * @param: seconds
     * @description: 过期
     * @author: kevinLiu
     * @date: 2021/8/9
     * @return: void
     */
    @Override
    public void expire(String fixedStr, long expireSeconds, TimeUnit seconds) {
        redisTemplate.expire(fixedStr, expireSeconds, seconds);
    }

    /**
     * @param: fixedStr
     * @param: limitingAccessCount
     * @param: currentTime
     * @description: 放入当前ip地址的访问时间
     * @author: kevinLiu
     * @date: 2021/8/9
     * @return: void
     */
    @Override
    public void putIpAddressCurTime(String fixedStr, String limitingAccessCount, Long currentTime) {
        redisTemplate.opsForHash().put(fixedStr, limitingAccessCount, currentTime.toString());
    }

    /**
     * @param: fixedStr
     * @param: limitingAccessCount
     * @param: count
     * @description: 放入ip地址的次数
     * @author: kevinLiu
     * @date: 2021/8/9
     * @return: void
     */
    @Override
    public void putIpAddressCurCount(String fixedStr, String limitingAccessCount, int count) {
        redisTemplate.opsForHash().put(fixedStr, limitingAccessCount, String.valueOf(count));
    }

    @Override
    public void operateLua(String cer) {
        //操作key
        String key = TOKEN_KEY + CertificateUtil.getUserIdByCer(cer);
        //lua脚本的参数，zset的大key，小key，currentTime，最大限制个数
        redisTemplate.execute(addTokenScript, Collections.singletonList(key),
                cer, String.valueOf(System.currentTimeMillis()), TOKEN_MAX_NUMBER);
    }

    /**
     * @param: ipAddress
     * @description: 查询ip地址的黑名单
     * @author: kevinLiu
     * @date: 2021/8/4
     */
    @Override
    public boolean queryBlackList(String ipAddress) {
        return redisTemplate.opsForValue().get(BLANK_LIST + ipAddress) == null;
    }

}
