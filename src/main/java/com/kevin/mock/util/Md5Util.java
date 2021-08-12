package com.kevin.mock.util;

import com.kevin.mock.error.exception.BizException;
import org.apache.commons.lang3.RandomStringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @description: MD5与盐值的工具类
 * @author: kevinLiu
 * @date: 2021/8/6
 */
public class Md5Util {

    private static Integer SALT_LENGTH = 4;

    /**
     * @description: 随机生成盐值
     * @author: kevinLiu
     * @date: 2021/8/6
     * @return: java.lang.String
     */
    public static String getNewSalt() {
        //随机生成盐值
        return RandomStringUtils.random(SALT_LENGTH, true, true).toUpperCase();
    }

    /**
     * @param: str
     * @param: salt
     * @description: 生成32位md5
     * @author: kevinLiu
     * @date: 2021/8/6
     * @return: java.lang.String
     */
    public static String string2Md5(String str, String salt) {
        str += salt;
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new BizException("The MD5 encryption algorithm failed. Procedure", e);
        }
        char[] charArray = str.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);

        StringBuffer hexValue = new StringBuffer();
        for (byte md5Byte : md5Bytes) {
            int val = ((int) md5Byte) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
}