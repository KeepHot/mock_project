package com.kevin.mock.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * @description: 时间格式转换工具类
 * @author: kevinLiu
 * @date: 2021/8/6
 */
public class LocalDateTimeUtil {

    /**
     * @param: localDateTime
     * @description: 将localDateTime的类型转为iso8601格式的字符串
     * @author: kevinLiu
     * @date: 2021/8/10
     */
    public static String localDateTimeToUtc(LocalDateTime localDateTime) {
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneOffset.UTC);
        return zonedDateTime.toString();
    }

}

