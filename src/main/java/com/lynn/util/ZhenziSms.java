package com.lynn.util;

import com.zhenzi.sms.ZhenziSmsClient;

import java.security.SecureRandom;
import java.util.Random;

public class ZhenziSms {

    static ZhenziSms instance;
    private static final String SYMBOLS = "0123456789"; // 验证码由范围内数字组成

    private static final Random RANDOM = new SecureRandom();

    public static ZhenziSms getInstance() {
        if (instance == null) {
            instance = new ZhenziSms();
        }
        return instance;
    }

    /**
     * 生成6位随机数，发送验证短信，并将验证码返回给控制层生成cookie
     */
    public String sendSMS(String phone) throws Exception {
        String code = getNonce_str();
        ZhenziSmsClient client = new ZhenziSmsClient("https://sms_developer.zhenzikj.com",
                "101456", "d09395be-b8f0-4285-9bb4-1c41c1d5fe23");

        String result = client.send(phone, "您的验证码为:"+code+"，5分钟内输入有效，立即注册");
        System.out.println("发送的结果为："+result);
        return  code;
    }

    /**
     * 获取长度为 6 的随机数字
     * @return 随机数字
     */
    public static String getNonce_str() {

        // 如果需要4位，那 new char[4] 即可，其他位数同理可得
        char[] nonceChars = new char[6];

        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }

        return new String(nonceChars);
    }

}
