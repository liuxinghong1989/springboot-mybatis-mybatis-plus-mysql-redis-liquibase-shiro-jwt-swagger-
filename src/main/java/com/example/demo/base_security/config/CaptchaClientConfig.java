package com.example.demo.base_security.config;

import com.example.demo.base_security.captha.CaptchaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liuxinghong
 * @Description: 验证码配置类
 * @date 2019/6/17 001715:53
 */
@Configuration
public class CaptchaClientConfig {

    @Bean
    public CaptchaClient getCaptchaClient(){
        return  new CaptchaClient.Builder().build();
    }

}
