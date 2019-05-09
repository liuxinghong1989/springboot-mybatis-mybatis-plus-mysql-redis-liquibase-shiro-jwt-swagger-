package com.example.demo.security.service;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;

/**
 * @author liuxinghong
 * @Description:
 * @date 2018/6/27/02718:26
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private RedisConnectionFactory connectionFactory;
    @Bean
    public RedisTokenStore tokenStore() {
        return new RedisTokenStore(connectionFactory);
    }
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


//    @Override
//    public void logOut(String mobileId ,String type) {
//        Collection<OAuth2AccessToken> mobiles = tokenStore().findTokensByClientIdAndUserName("mobile",mobileId);
//        Collection<OAuth2AccessToken> tokens = tokenStore().findTokensByClientIdAndUserName("verification", mobileId);
//        List<OAuth2AccessToken> result = Lists.newArrayList();
//        if(!CollectionUtils.isEmpty(mobiles)) {
//            result.addAll(mobiles);
//        }
//        if (!CollectionUtils.isEmpty(tokens)){
//            result.addAll(tokens);
//        }
//        if(!CollectionUtils.isEmpty(result)){
//            for(OAuth2AccessToken token : result) {
//                tokenStore().removeAccessToken(token);
//            }
//        }
//        stringRedisTemplate.boundValueOps(ResourceServerConfig.USER_STATUS_KEY+mobileId).set(type,1, TimeUnit.DAYS);
//    }

    /**
     * 登陆按照登陆类型清除之前token
     * (废弃，根据手机号清除密码和验证登陆的所有token)
     * @param mobile
     * @param loginType
     */
    @Override
    public void cleanToken(String mobile, String loginType) {
        List<OAuth2AccessToken> result = Lists.newArrayList();
        Collection<OAuth2AccessToken> tokens = null;
        if(StringUtils.equals("1",loginType)){//手机密码登陆
            tokens = tokenStore().findTokensByClientIdAndUserName("mobile",mobile);
        }
        if(StringUtils.equals("2",loginType)){//手机验证码登陆
            tokens = tokenStore().findTokensByClientIdAndUserName("verification",mobile);
        }
        if (!CollectionUtils.isEmpty(tokens)){
            result.addAll(tokens);
            for(OAuth2AccessToken token : result) {
                tokenStore().removeAccessToken(token);
            }
        }
    }
}
