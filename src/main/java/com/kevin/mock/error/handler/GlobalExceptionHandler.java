package com.kevin.mock.error.handler;

import com.kevin.mock.constant.ErrorCode;
import com.kevin.mock.dto.common.result.ResponseResult;
import com.kevin.mock.error.exception.BizException;
import com.kevin.mock.error.exception.IpAddressAccessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Response;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import static com.kevin.mock.constant.ConstantField.*;


/**
 * @description: 全局异常处理类
 * @author: kevinLiu
 * @date: 2021/8/3
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {



    /**
     * @param: exception
     * @description: 不支持的媒体类型，仅支持JSON的数据类型
     * @author: kevinLiu
     * @date: 2021/8/11
     * @return: com.kevin.mock.dto.common.result.ResponseResult
     */
    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    public Response exceptionHandler(HttpServletRequest request,HttpMediaTypeNotSupportedException exception) {
        //获取参数默认的信息
        log.error("MediaTypeNotSupport occurs:HttpRequest:{}.Current system time:{}.Thread information:{}.MediaNotSupportMessage:{}",
                request,new Date(), Thread.currentThread().getName(), exception);
        Response response = new Response();
        response.setStatus(415);
        return response;
    }


    /**
     * @param: duplicateKeyException
     * @description: 参数校验异常的处理
     * @author: kevinLiu
     * @date: 2021/8/6
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseResult exceptionHandler(HttpServletRequest request, MethodArgumentNotValidException bindException) {
        //获取参数默认的信息
        String defaultMessage = bindException.getBindingResult().getFieldError().getDefaultMessage();
        log.error("parameter bind exception occurs:HttpRequest:{}. Current system time:{}.Thread information:{}.BindParameterMessage:{}.BindExceptionMsg:{}",
                request ,new Date(), Thread.currentThread().getName(), defaultMessage, bindException);
        //从map中获取ErrorCode来进行
        ErrorCode errorCode = messageMap.get(defaultMessage);
        if (errorCode != null) {
            return ResponseResult.error(errorCode);
        }
        return ResponseResult.error(ErrorCode.SERVER_INTERNAL_ERROR);
    }

    /**
     * @param: ipAddressAccessException
     * @description: 业务异常
     * @author: kevinLiu
     * @date: 2021/8/6
     * @return: com.kevin.mock.dto.common.result.ResponseResult
     */
    @ExceptionHandler(value = BizException.class)
    public ResponseResult exceptionHandler(HttpServletRequest request,BizException bizException) {
        log.error("bizException occurs:HttpRequest:{}.Current system time:{}.Thread information:{}.StackMessage:{}",
                request,new Date(), Thread.currentThread().getName(), bizException);
        return ResponseResult.error(ErrorCode.EMAIL_HAS_REGISTERED);
    }

    /**
     * @param: ipAddressAccessException
     * @description: IP地址访问超过限制异常
     * @author: kevinLiu
     * @date: 2021/8/6
     * @return: com.kevin.mock.dto.common.result.ResponseResult
     */
    @ExceptionHandler(value = IpAddressAccessException.class)
    public ResponseResult exceptionHandler(HttpServletRequest request,IpAddressAccessException ipAddressAccessException) {
        log.error("ipAddressAccess exception occurs:HttpRequest:{}.Current system time:{}.Thread information:{}." +
                        "StackMessage:{}.IPAddress:{}.AccessCount:{}",
                request,new Date(), Thread.currentThread().getName(), ipAddressAccessException,
                ipAddressAccessException.getIpAddress(), ipAddressAccessException.getCount());
        return ResponseResult.error(ErrorCode.SERVER_BUSY);
    }

    /**
     * @param: exception
     * @description: 处理其他异常 如数据库异常，Redis异常
     * @author: kevinLiu
     * @date: 2021/8/5
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseResult exceptionHandler(HttpServletRequest request,Exception exception) {
        log.error("unknown exception :HttpRequest:{}.Current system time:{}.Thread information:{}.StackMessage:{}.",
                request,new Date(), Thread.currentThread().getName(), exception);
        return ResponseResult.error(ErrorCode.SERVER_INTERNAL_ERROR);
    }
}
