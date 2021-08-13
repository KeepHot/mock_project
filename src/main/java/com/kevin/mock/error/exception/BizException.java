package com.kevin.mock.error.exception;

/**
 * @description: 异常
 * @author: kevinLiu
 * @date: 2021/8/10
 */
public class BizException extends RuntimeException {
    private String message;
    private Exception exception;
    public BizException(String message, Exception exception) {
        super(message);
        this.exception = exception;
        this.message = message;
    }
}
