package com.yan.architect.architectdemo.common.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.net.URLDecoder;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA工具类
 *
 */
public class RSAUtils {
	
	
	
	//////////////////////   测试 start  ////////////////////////////////
    public static void main(String[] args) throws Exception {
		// 后端接收到加密后的密码串
    	String encodedData = "i0bpHA%2BEs%2BWwLZSIKyxTZ%2BiBcLCVdQCB4a5tUDl448MNS8DQU%2BMEYBezdELG22dpbk6cyZEIlUipZQKGViy9jlonnNLN26CCqSFWYr8Wfhy%2FCLRT%2F2mHteC%2F46GyPzgjYTxIzg5%2BU0ZX1hhoh19flP0TMxIMOUrz5X7AZ9yrbog%3D ";
    	String jiamiDec = URLDecoder.decode(encodedData, "UTF-8");
    	
		byte[] decodedData = RSAUtils.decryptByPrivateKey(jiamiDec, PRIVATE_KEY);
        String outputStr = new String(decodedData);
        System.err.println("解密后: " + outputStr);
	}
   
	
    private static String publicKey;
    private static String privateKey;
    
    public static void setUp() throws Exception {
        Map<String, Key> keyMap = RSAUtils.initKey();
        publicKey = RSAUtils.getPublicKey(keyMap);
        privateKey = RSAUtils.getPrivateKey(keyMap);
        System.err.println("公钥: \n\r" + publicKey);
        System.err.println("私钥： \n\r" + privateKey);
    }

    public static void test() throws Exception {
        System.err.println("公钥加密——私钥解密");
        String inputStr = "www.Baidu.com$";
        byte[] encodedData = RSAUtils.encryptByPublicKey(inputStr, publicKey);
        byte[] decodedData =RSAUtils.decryptByPrivateKey(encodedData,
                privateKey);
        String outputStr = new String(decodedData);
        System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);
        System.err.println(inputStr.equals(outputStr));
    }

    public static void testSign() throws Exception {
        System.err.println("私钥加密——公钥解密");
        String inputStr = "www.Hao123.com$";
        byte[] data = inputStr.getBytes();
        byte[] encodedData =RSAUtils.encryptByPrivateKey(data, privateKey);
        byte[] decodedData = RSAUtils.decryptByPublicKey(encodedData, publicKey);
        String outputStr = new String(decodedData);
        System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);
        System.err.println(inputStr.equals(outputStr));
        
        
        System.err.println("私钥签名——公钥验证签名");
        // 产生签名
        String sign = RSAUtils.sign(encodedData, privateKey);
        System.err.println("签名:" + sign);
        // 验证签名
        boolean status = RSAUtils.verify(encodedData, publicKey, sign);
        System.err.println("状态:" + status);
    }
    //////////////////////   测试 end  ////////////////////////////////
	
	
	
	
	
	
	
	public static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDAMKtvR/reDjI8UUMbmVe934VRjntkTVBPf0mdivB87/HAADzxvBfaD0Q2oTX3kxOExpkmNqIoAdjzN3ZNbXQX/5ZvMXDumsMQgxE7aRhC+a5DbMz05K8wyFhZPbeTYc7nOe+8GErHwd+F5ZcpstXV8oAR9xkZkngrGu+nJVo/5QIDAQAB";
	public static final String PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMAwq29H+t4OMjxRQxuZV73fhVGOe2RNUE9/SZ2K8Hzv8cAAPPG8F9oPRDahNfeTE4TGmSY2oigB2PM3dk1tdBf/lm8xcO6awxCDETtpGEL5rkNszPTkrzDIWFk9t5Nhzuc577wYSsfB34Xllymy1dXygBH3GRmSeCsa76clWj/lAgMBAAECgYBXqxvYlEYMWv9xYkQxZ76Nf1OqQ0toEphq2w5yjflrlGx7f+BNCNb8gnBEVz9t4qpJq+oac1t+0nDraUZOxXnp2gyyAbzpOp8VXwZLb0BRCYaUNGfs7ispscwlYhlBeOykdiVK1dELYCm9GF/Xcw3aECVHpmPKc3z/Br4TBHuCFQJBAOY8+Yv4AU5wJw2VadryER9Cmt5POUa9/sXPcm4vVZfXJNPZYD7JHRZdIEp0wgFUVcxelVTKr/HTWWWYqoNpwl8CQQDVsdUW2epL4myoCSS3qFzx8n1ukxhvDqmw5MI1gdKTJzJf1paIdLM/pR5vdkruHUZP+gvH8mJWdvMf5pQR4gw7AkEAtf4k0/sqLTKB7D7a+fK2jhs8B9Zf1AeDSCF+tpI7+lZaCgueBIM8ttzOFYzNffeICpAJQoklIuaJl8Fhp5cFyQJAAq78BRBxf8h1ItsSiffkSumHp7bTLLnbaaxFwBRER7LLUCbKtyX4BkmVi0LKCBl4A5MZSMaDc6CExxbHLz5OdwJAPnxjvacwcTRzmuprhxgCv5QZAHeC+KgCVgpj8/hBcJvpx6ve0gxBAdqO5P7dVBtZanLB+TRnFvfFKfZ3IoorKQ==";

    /** 
     * 密钥长度，DH算法的默认密钥长度是1024 
     * 密钥长度必须是64的倍数，在512到65536位之间 
     * */  
    private static final int KEY_SIZE = 512; 
    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    private static final String PUBLIC_KEY_PARAM = "RSAPublicKey";
    private static final String PRIVATE_KEY_PARAM = "RSAPrivateKey";

    public static byte[] decryptBASE64(String key) {
        return Base64.decodeBase64(key);
    }

    public static String encryptBASE64(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }

    /**
     * 用私钥对信息生成数字签名
     *
     * @param data       加密数据
     * @param privateKey 私钥
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        // 解密由base64编码的私钥
        byte[] keyBytes = decryptBASE64(privateKey);
        // 构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 取私钥匙对象
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(priKey);
        signature.update(data);
        return encryptBASE64(signature.sign());
    }

    /**
     * 校验数字签名
     *
     * @param data      加密数据
     * @param publicKey 公钥
     * @param sign      数字签名
     * @return 校验成功返回true 失败返回false
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign)
            throws Exception {
        // 解密由base64编码的公钥
        byte[] keyBytes = decryptBASE64(publicKey);
        // 构造X509EncodedKeySpec对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 取公钥匙对象
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(data);
        // 验证签名是否正常
        return signature.verify(decryptBASE64(sign));
    }

    public static byte[] decryptByPrivateKey(byte[] data, String key) throws Exception{
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 解密<br>
     * 用私钥解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(String data, String key)
            throws Exception {
        return decryptByPrivateKey(decryptBASE64(data),key);
    }

    /**
     * 解密<br>
     * 用公钥解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] data, String key)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    /**
     * 加密<br>
     * 用公钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(String data, String key)
            throws Exception {
        // 对公钥解密
        byte[] keyBytes = decryptBASE64(key);
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data.getBytes());
    }

    /**
     * 加密<br>
     * 用私钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String key)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 取得私钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Key> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY_PARAM);
        return encryptBASE64(key.getEncoded());
    }

    /**
     * 取得公钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Key> keyMap)
            throws Exception {
        Key key = keyMap.get(PUBLIC_KEY_PARAM);
        return encryptBASE64(key.getEncoded());
    }

    /**
     * 初始化密钥
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Key> initKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(KEY_SIZE);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        Map<String, Key> keyMap = new HashMap<String, Key>(2);
        keyMap.put(PUBLIC_KEY_PARAM, keyPair.getPublic());// 公钥
        keyMap.put(PRIVATE_KEY_PARAM, keyPair.getPrivate());// 私钥
        return keyMap;
    }
    
}