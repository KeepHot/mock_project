package com.kevin.mock.util;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.kevin.mock.constant.ConstantField.FIGURE_PATTERN;
import static com.kevin.mock.constant.ConstantField.TOKEN_LENGTH;

/**
 * @description: 凭证生成工具类
 * @author: kevinLiu
 * @date: 2021/8/2
 */
public class CertificateUtil {

    /**
     * @param: userId
     * @description: 创建一个新的凭证
     * @author: kevinLiu
     * @date: 2021/8/4
     */
    public static String createNewCertification(Integer userId) {
        return userId + " " + UUIDUtil.getNewUuid().replaceAll("-", "").substring(0, 16) +
                String.valueOf(System.currentTimeMillis()).substring(9, 13);
    }

    /**
     * @param: str
     * @description: 返回凭证中的userId
     * @author: kevinLiu
     * @date: 2021/8/4
     */
    public static String getUserIdByCer(String str) {
        StringTokenizer stringTokenizer = new StringTokenizer(str, " ");
        return stringTokenizer.nextToken();
    }


    /**
     * @param: str
     * @description: 判断凭证的格式是否正确
     * @author: kevinLiu
     * @date: 2021/8/4
     */
    public static boolean isLegalCertification(String str) {
        if (null==str) {
            return false;
        }
        //判断凭证的空格前半部分（是否为主键）
        StringTokenizer stringTokenizer = new StringTokenizer(str, " ");
        String userId = stringTokenizer.nextToken();
        //确保主键部分是纯数字
        Pattern pattern = FIGURE_PATTERN;
        Matcher isNum = pattern.matcher(userId);
        if (!isNum.matches()) {
            return false;
        }
        //判断凭证的空格的后半部分
        String token = stringTokenizer.nextToken();
        if (token.length() != TOKEN_LENGTH) {
            return false;
        }
        //确定uuid以及最后四位的时间戳
        String uuid =token.substring(0, 16);
        String timeStampStr = token.substring(17, 20);
        //确保uuid为数字和字母组合
        String reg = "^[a-zA-Z0-9]{6,16}$";
        if (!uuid.matches(reg)) {
            return false;
        }
        //确保后四位是纯数字
        Matcher isNumber = pattern.matcher(timeStampStr);
        if (!isNumber.matches()) {
            return false;
        }
        return true;
    }
}
