package com.example.demo.security.config;

import com.example.demo.entity.SysUser;
import com.example.demo.entity.User;
import com.example.demo.security.Dto.UserInfoDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import java.util.HashMap;
import java.util.Map;

/**
 * token生成携带的信息
 * @author Administrator
 * @date 2018/2/2
 */
public class CustomTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Authentication userAuthentication = authentication.getUserAuthentication();
        if(userAuthentication != null) {
            final Map<String, Object> additionalInfo = new HashMap<>();
            UserInfoDTO user = (UserInfoDTO) userAuthentication.getPrincipal();
            additionalInfo.put("id",user.getId());
            additionalInfo.put("name", user.getName());
            additionalInfo.put("username", user.getUsername());
//          additionalInfo.put("authorities", user.getAuthorities());
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        }
        return accessToken;
    }
}
