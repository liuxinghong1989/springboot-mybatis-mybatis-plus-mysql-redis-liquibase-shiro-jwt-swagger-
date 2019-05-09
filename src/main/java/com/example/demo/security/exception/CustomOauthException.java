package com.example.demo.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * @author zhuyanwei
 */
@JsonSerialize(using = CustomOauthExceptionSerializer.class)
public class CustomOauthException extends OAuth2Exception {

    private String message;
    private Integer code;
    public CustomOauthException(String msg) {
        super(msg);
    }
    public CustomOauthException(String msg, Throwable t) {
        super(msg, t);
        if(t instanceof InvalidGrantException) {
            code = 4010;
            message = "输入密码错误！";
        } else {
            message = msg;
            code = 5000;
        }
    }
    @Override
    public String getMessage() {
        return message;
    }
    public Integer getCode() {
        return code;
    }
}
