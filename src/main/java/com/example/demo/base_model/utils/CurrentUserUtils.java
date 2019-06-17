package com.example.demo.base_model.utils;

import com.example.demo.base_model.entity.SysUser;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liuxinghong
 * @Description: 获取当前用用信息
 * @date 2019/5/16 001617:40
 */
public class CurrentUserUtils {

    public static SysUser getUser(){
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = servletRequestAttributes.getRequest();
        SysUser user = (SysUser)request.getAttribute("currentUser");
        if (null != user){
            return user;
        }
        return null;
    }
}
