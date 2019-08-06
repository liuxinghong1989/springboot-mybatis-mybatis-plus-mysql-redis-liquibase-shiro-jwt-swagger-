package com.example.demo.base_model.utils;

import com.example.demo.base_model.entity.SysUser;
import com.example.demo.base_model.service.SysUserService;
import com.example.demo.base_security.util.JWTUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author liuxinghong
 * @Description: 获取当前线程登录用户信息
 * @date 2019/5/16 001617:40
 */
public class CurrentUserUtils {

    /**
     * 获取当前登录用户的信息
     * @return
     */
    public static Object getCurrentUser() {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (Objects.nonNull(principal) && StringUtils.isNotBlank(String.valueOf(principal))){
            String userId = JWTUtil.getUserId(String.valueOf(principal));
            return userId;
        }
        return null;
    }
}
