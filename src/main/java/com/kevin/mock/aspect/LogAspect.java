package com.kevin.mock.aspect;

import com.kevin.mock.dto.req.LoginReqDTO;
import com.kevin.mock.dto.req.RegisterReqDTO;
import com.kevin.mock.dto.req.UpdatePwdReqDTO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.kevin.mock.constant.ConstantField.X_AUTHORIZATION;

/**
 * 日志切面
 *
 * @author: kevinLiu
 * @date: 2021/8/12
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    @Pointcut("execution(public * com.kevin.mock.service.impl.UserServiceImpl.*(..))")
    public void logAspect() {
    }

    /**
     * 用于打印方法执行前后的日志
     *
     * @author: kevinLiu
     * @date: 2021/8/12
     */
    @Around(value = "logAspect()")
    public Object logAspectAround(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取Request请求
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        //参数脱敏
        Object[] paramArgs = joinPoint.getArgs();
        List<Object> beforeArray = Arrays.asList(paramArgs);
        Object loginOrRegisterObject = beforeArray.get(0);
        Object updateObject = null;
        if (beforeArray.size()>=2){
            updateObject = beforeArray.get(1);
        }
        if (loginOrRegisterObject instanceof LoginReqDTO) {
            LoginReqDTO params = (LoginReqDTO) loginOrRegisterObject;
            paramArgs[0] = params.getEmail();
        } else if (loginOrRegisterObject instanceof RegisterReqDTO) {
            RegisterReqDTO params = (RegisterReqDTO) loginOrRegisterObject;
            paramArgs[0] = params.getEmail();
        } else if (updateObject instanceof UpdatePwdReqDTO) {
            paramArgs[1] = null;
        }
        //执行方法前打印
        log.info("execution method before：token:{},requestTime:{},http_method:{},invoke_method:{},args:{}",
                request.getHeader(X_AUTHORIZATION),
                System.currentTimeMillis(),
                request.getMethod(),
                joinPoint.getSignature().getName(),
                paramArgs);
        Object result = joinPoint.proceed();
        //执行方法后打印
        log.info("execution method after：token:{},requestTime:{},http_method:{},invoke_method:{},args:{},response:{}",
                request.getHeader(X_AUTHORIZATION),
                System.currentTimeMillis(),
                request.getMethod(),
                joinPoint.getSignature().getName(),
                paramArgs,
                result.toString());
        return result;
    }

}
