package com.example.demo.utils;

import com.baomidou.mybatisplus.extension.api.R;
import com.example.demo.entity.SysUser;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import springfox.documentation.RequestHandler;

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
