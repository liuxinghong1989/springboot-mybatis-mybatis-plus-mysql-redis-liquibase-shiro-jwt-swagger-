package com.example.demo.base_security.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @author liugh
 * @since 2018-05-07
 */
public class JWTUtil {

    // 过期时间1天
    private static final long EXPIRE_TIME = 24*60*60*1000;

    /**
     * 校验token是否正确
     * @param token 密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, String userNo, String secret) {
        try {
            if (StringUtils.startsWith(token,"Bearer") || StringUtils.startsWith(token,"bearer")){
                String resultToken;
                if (StringUtils.startsWith(token,"B")){
                    resultToken = token.split("Bearer")[1].trim();
                }else {
                    resultToken = token.split("bearer")[1].trim();
                }
                Algorithm algorithm = Algorithm.HMAC256(secret);
                JWTVerifier verifier = JWT.require(algorithm)
                        .withClaim("id", userNo)
                        .build();
                verifier.verify(resultToken);
                return true;
            }else {
                return false;
            }
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     * @return token中包含的用户id
     */
    public static String getUserNo(String token) {
        try {
            String resultToken;
            if (StringUtils.startsWith(token,"B")){
                 resultToken = token.split("Bearer")[1].trim();
            }else {
                 resultToken = token.split("bearer")[1].trim();
            }
            DecodedJWT jwt = JWT.decode(resultToken);
            return jwt.getClaim("id").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名,指定时间后过期,一经生成不可修改，令牌在指定时间内一直有效
     * @param id 用户主键
     * @param secret 用户的密码
     * @return 加密的token
     */
    public static String sign(String id, String secret) {
        try {
            Date date = new Date(System.currentTimeMillis()+EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // 附带用户id信息
            String token = JWT.create()
                    .withClaim("id", id)
                    .withExpiresAt(date)
                    .sign(algorithm);
            return "Bearer "+token;
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}
