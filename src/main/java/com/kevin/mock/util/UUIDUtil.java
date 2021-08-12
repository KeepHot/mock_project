package com.kevin.mock.util;

import java.util.*;

/**
 * @description: UUID生成工具类
 * @author: kevinLiu
 * @date: 2021/8/10
 */
public class UUIDUtil {
    /**
     * 获取一个新的UUID
     */
    public static String getNewUuid() { return UUID.randomUUID().toString(); }

}
