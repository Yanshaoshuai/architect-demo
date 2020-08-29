package com.yan.architect.architectdemo.common.utils;

import com.yan.architect.architectdemo.common.exception.BusinessException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

/**
 * Jwt工具类
 */
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    //密钥
    public static final String SECRETKEY = "99ff1050c97923cd1b33a13df39e63e65a4f221006c9850f";
    
    /**
     * createToken
     * @return
     */
    public static String createToken(String subject, long userId,String password) {
    	JwtBuilder builder = Jwts.builder()
			.setSubject(subject)
	        .claim("account", subject)
	        .claim("uid", userId)
	        .claim("password", password)
	        .setIssuedAt(new Date())
	        .signWith(SignatureAlgorithm.HS256, SECRETKEY);
    	
        return builder.compact().toString();
    }

    /**
     * 验证token合法性
     *
     * @param token
     * @return
     */
    public static boolean verifyToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(SECRETKEY).parseClaimsJws(token).getBody();
            return true;
        } catch (Exception e) {
            logger.error("---方法verifyToken---错误信息：" + e.getMessage(), e);
            if (e.getMessage().contains("JWT expired")) {
                throw new BusinessException(500,"JWT expired");
            }
            if (e.getMessage().contains("not match locally computed signature")) {
                throw new BusinessException(500,"keyNotMatch");
            }
            return false;
        }
    }

    /**
     * 解析被压缩的token值并获取userId
     *
     * @param token
     * @return
     */
    public static int getUserIdByToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(SECRETKEY).parseClaimsJws(token).getBody();
            return (int) claims.get("userId");
        } catch (Exception e) {
            logger.error("---getSessionPoJoByToken---错误信息：" + e.getMessage(), e);
            if (e.getMessage().contains("JWT expired")) {
                throw new BusinessException(500,"登录信息失效");
            }
            if (e.getMessage().contains("not match locally computed signature")) {
                throw new BusinessException(500,"签名不匹配");
            }
            throw e;
        }
    }

    /**
     * 根据自定义的Constant的字符串生成的Key
     *
     * @param salt
     * @return
     */
    private static Key generalKey(String salt) {
        if (null == salt || "".equals(salt)){
            return null;
        }
        salt = "marcopolo" + salt;
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
        String printBase64Binary = DatatypeConverter.printBase64Binary(salt.getBytes());
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(printBase64Binary);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        return signingKey;
    }
}
