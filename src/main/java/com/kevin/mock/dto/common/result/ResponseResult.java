package com.kevin.mock.dto.common.result;

import com.kevin.mock.constant.ErrorCode;
import lombok.*;

import java.io.Serializable;

import static com.kevin.mock.constant.ErrorCode.SERVER_SUCCESS;

/**
 * @description: 统一返回格式
 * @author: kevinLiu
 * @date: 2021/8/5
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseResult<T> implements Serializable {

    private static final long serialVersionUID = 7497549174712832106L;

    private Integer code;
    private String msg;
    private T result;

    public ResponseResult(ErrorCode statusCode, T t) {
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
        this.result = t;
    }

    public static ResponseResult error(ErrorCode statusCode) {
        return new ResponseResult<>(statusCode, null);
    }

    /**
     * @param:
     * @description: 判断是Token校验否成功
     * @author: kevinLiu
     * @date: 2021/8/5
     * @return: boolean
     */
    public static boolean isSuccess(ResponseResult result) {
        Integer integer = result.getCode();
        Integer expectRes = SERVER_SUCCESS.getCode();
        return !integer.equals(expectRes);
    }
}
