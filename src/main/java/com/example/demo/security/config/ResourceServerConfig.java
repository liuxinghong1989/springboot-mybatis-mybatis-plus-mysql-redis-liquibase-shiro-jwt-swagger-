package com.example.demo.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ZYW
 * @date 2018/2/2
 */
@Slf4j
@Configuration
@EnableOAuth2Client
@EnableResourceServer
//开启基于方法的声明式权限控制
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Value("${jwt_token_key}")
    private String tokenKey;

    public static String USER_STATUS_KEY = "USER_STATUS_KEY:";
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //放行接口
    private static final String[] AUTH_WHITELIST = {

            // -- swagger ui
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**",
            //业务放行接口
            //资源下载
            // "/auth/oauth/token",
            "/file/download/**"

    };


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> {
                    Map<String,String> map = new HashMap<>();
                    map.put("code", "401");
                    map.put("message", "登陆已过期，请重新登陆");
                    map.put("timestamp", String.valueOf(System.currentTimeMillis()));
                    response.setContentType("application/json;charset=UTF-8");
                    response.setHeader("Access-Control-Allow-Origin","*");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    try {
                        log.info(new ObjectMapper().writeValueAsString(map));
                        response.getOutputStream().write(new ObjectMapper().writeValueAsString(map).getBytes());
                    } catch (Exception e) {
                        throw new ServletException();
                    }
                })
                .and()
                .authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic().disable();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.authenticationEntryPoint((request,response,authException) -> {
            String resultToken;
            String token = request.getHeader("Authorization");
            if(StringUtils.isNotEmpty(token)){
                boolean b = token.startsWith("B");
                if(b){
                    resultToken = token.split("Bearer")[1].trim();
                }else{
                    resultToken = token.split("bearer")[1].trim();
                }
            }else{
                resultToken = request.getParameter("access_token");
            }
            String message;
            try {
                Claims claims = Jwts.parser().setSigningKey(tokenKey.getBytes("UTF-8")).parseClaimsJws(resultToken).getBody();
                String username = String.valueOf(claims.get("username"));
                String type = stringRedisTemplate.boundValueOps(USER_STATUS_KEY+username).get();
                stringRedisTemplate.delete(USER_STATUS_KEY+username);
                if(StringUtils.equals("1",type)) {
                    message ="用户被禁用";
                } else if(StringUtils.equals("2",type)) {
                    message ="用户已在其他终端登陆";
                } else {
                    message ="登陆已过期，请重新登陆";
                }
            } catch (ExpiredJwtException e) {
                message ="登陆已过期，请重新登陆";
            } catch (UnsupportedJwtException e) {
                log.error("token非法",e);
                message ="登陆已过期，请重新登陆";
            } catch (MalformedJwtException e) {
                log.error("jwt未能正常被构造",e);
                message ="非法登陆";
            } catch (IllegalArgumentException e) {
                log.error("token参数非法",e);
                message ="非法登陆";
            }
            Map<String,String> map = new HashMap<>();
            map.put("code", "401");
            map.put("message", message);
            map.put("timestamp", String.valueOf(System.currentTimeMillis()));
            response.setContentType("application/json;charset=UTF-8");
            response.setHeader("Access-Control-Allow-Origin","*");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            try {
                log.info(new ObjectMapper().writeValueAsString(map));
                response.getOutputStream().write(new ObjectMapper().writeValueAsString(map).getBytes());
            } catch (Exception e) {
                throw new ServletException();
            }
        });
    }

}
