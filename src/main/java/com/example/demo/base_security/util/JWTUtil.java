package com.example.demo.base_security.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.sql.Savepoint;
import java.util.Date;

/**
 * @author liugh
 * @since 2018-05-07
 */
@Component
public class JWTUtil {
    private static RedisTemplate redisTemplate;
    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        JWTUtil.redisTemplate = redisTemplate;
    }

    // 过期时间(秒)
    private static Long expireTime;
    @Value("${expireTime}")
    private void  getTime(Long expireTime){
        JWTUtil.expireTime =expireTime;
    }
  //  private static final long EXPIRE_TIME = 15*24*60*60*1000;

    /**
     * 校验token是否正确
     * @param token 密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, String userNo,String loginName, String secret) {
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
                        .withClaim("loginName",loginName)
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
    public static String sign(String id,String loginName, String secret) {
        try {
            Date date = new Date(System.currentTimeMillis()+expireTime);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // 附带用户id信息
            String token = "Bearer "+JWT.create()
                    .withClaim("id", id)
                    .withClaim("loginName",loginName)
                    .withExpiresAt(date)
                    .sign(algorithm);
            //存放在redis里面(实现单端登录)
            saveRedis(id,token);
            return token;
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    private static void saveRedis(String id, String token) {
        Object token1 = redisTemplate.opsForHash().get("token", id);
        if (null != token1 && StringUtils.isNotBlank(String.valueOf(token1))){
            redisTemplate.opsForHash().delete("token",id);
        }
        redisTemplate.opsForHash().put("token",id,token);
    }
}
