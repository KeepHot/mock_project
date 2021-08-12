package com.kevin.mock.aspect;

import com.kevin.mock.annotation.IPAccessLimiter;
import com.kevin.mock.error.exception.IpAddressAccessException;
import com.kevin.mock.service.RedisService;
import com.kevin.mock.util.AliYunSmsUtil;
import com.kevin.mock.util.IpAddressUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @description: IP限流切面注解
 * @author: kevinLiu
 * @date: 2021/8/9
 */
@Slf4j
@Aspect
@Component
public class IpAccessLimiterAspect {

    private static final String LIMITING_KEY = "limiting:%s:%s";
    private static final String LIMITING_BEGIN_TIME = "access:begin";
    private static final String LIMITING_ACCESS_COUNT = "access:count";

    private final RedisService redisDao;

    public IpAccessLimiterAspect(RedisService redisDao) {
        this.redisDao = redisDao;
    }

    @Pointcut("@annotation(ipAccessLimiter)")
    public void pointCut(IPAccessLimiter ipAccessLimiter) {
    }

    @Around(value = "pointCut(ipAccessLimiter)", argNames = "pjp,ipAccessLimiter")
    public Object around(ProceedingJoinPoint pjp, IPAccessLimiter ipAccessLimiter) throws Throwable {

        //查询是否该ip地址存在黑名单中(定时失效)
        String ipAddress = IpAddressUtil.getIpAddress();
        String currentMethodName = pjp.getSignature().toLongString();
        boolean isBlackList = redisDao.queryBlackList(ipAddress);
        if (!isBlackList) {
            log.warn("黑名单中的:" + ipAddress + "正在访问" + currentMethodName + "接口,已经被禁止");
            throw new IpAddressAccessException(ipAddress,ipAccessLimiter.messages());
        }

        //Ip限流的固定参数
        int maxAccessCount = ipAccessLimiter.maxAccessCount();
        long fixedCycleMilliSecond = ipAccessLimiter.fixedCycleMilliSecond();
        Long currentTime = System.currentTimeMillis();
        String fixedStr = String.format(LIMITING_KEY, ipAddress, currentMethodName);

        //获取该ip地址访问该方法名的首次时间和执行次数并进行赋值
        Long accessBeginTimeLong = redisDao.tryGetBeginTime(fixedStr, LIMITING_BEGIN_TIME);
        Integer accessCountInteger = redisDao.tryGetAccessCount(fixedStr, LIMITING_ACCESS_COUNT);
        Long beginTime = (accessBeginTimeLong == null) ? 0L : accessBeginTimeLong;
        int hasAccessCount = (accessCountInteger == null) ? 0 : accessCountInteger;

        //当前时间减去开始时间并且与周期时间进行比较，如果大于正常访问并初始化为当前时间与次数
        if (currentTime - beginTime > fixedCycleMilliSecond) {
            redisDao.putIpAddressCurTime(fixedStr, LIMITING_BEGIN_TIME, currentTime);
            redisDao.putIpAddressCurCount(fixedStr, LIMITING_ACCESS_COUNT, 1);
            redisDao.expire(fixedStr, ipAccessLimiter.expireSeconds(), TimeUnit.SECONDS);
            return pjp.proceed();
        } else { //在时间周期之内，并且已访问次数小于最大访问次数
            if (hasAccessCount < maxAccessCount) {
                redisDao.putIpAddressCurCount(fixedStr, LIMITING_ACCESS_COUNT, hasAccessCount + 1);
                return pjp.proceed();
            } else {
                //将该ip地址加入黑名单,并设置有效期60s，禁止访问并发送短信警告
                redisDao.putBlackList(ipAddress);
                String warnMessage = String.format("warming message:ipAddress:{},interface:{}",ipAddress,currentMethodName);
                AliYunSmsUtil.sendSms("17782161746", warnMessage);
                log.warn("UserService:IP地址:{}正在频繁调用{}接口" + ipAddress, currentMethodName);
                throw new IpAddressAccessException(ipAddress,ipAccessLimiter.messages());
            }
        }
    }
}