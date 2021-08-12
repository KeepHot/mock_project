package com.kevin.mock.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

/**
 * @description: 阿里云短信服务
 * @author: kevinLiu
 * @date: 2021/8/3
 */
public class AliYunSmsUtil {

    private static final String PRODUCT = "Dysmsapi";
    private static final String DOMAIN = "dysmsapi.aliyuncs.com";

    private static final String ACCESS_KEY_ID  = "LTAI4GDUUbrhiWF66YhNAT9q";
    private static final String ACCESS_KEY_SECRET = "7u3ibOnxT5JETnXzQHOyyFlaRKw2z3";

    public static void sendSms(String telephone, String code) throws ClientException {
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou",
                ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou",
                PRODUCT, DOMAIN);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(telephone);
        request.setSignName("树莓研究所");
        request.setTemplateCode("SMS_200181017");
        request.setTemplateParam("{\"code\":\"" + code + "\"}");
        request.setOutId("yourOutId");
        acsClient.getAcsResponse(request);
    }
}