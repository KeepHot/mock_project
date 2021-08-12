package com.kevin.mock.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: http请求工具类
 * @author: kevinLiu
 * @date: 2021/7/30
 */
public class HttpRequestUtil {

    /**
     * @param: request
     * @description: 获取http请求头信息
     * @author: kevinLiu
     * @date: 2021/7/30
     */
    public static Map<String, String> getHeadersInfo(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>(16);
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }
}
