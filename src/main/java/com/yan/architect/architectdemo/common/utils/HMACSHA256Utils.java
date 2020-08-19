package com.yan.architect.architectdemo.common.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HMACSHA256Utils {
    public static String HMACSHA256(String data) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        String key = "truechain-light-wallet-server";

        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);

        byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
        return new Base64().encodeToString(array);
    }

    /**
     * 测试方法
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        System.out.println(HMACSHA256("123456"));
    }

}
