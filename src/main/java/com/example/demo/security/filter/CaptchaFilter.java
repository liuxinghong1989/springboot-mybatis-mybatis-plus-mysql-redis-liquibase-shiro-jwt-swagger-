//package com.example.demo.security.filter;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//
///**
// * @author zyw
// * @date 2018/2/9
// */
//@Component
//@Slf4j
//public class CaptchaFilter implements Filter {
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
//        String uri = httpServletRequest.getRequestURI();
//        if (uri.contains("/oauth/token")) {
//            //登陆放行并且判断验证码
//            try {
//                filterChain.doFilter(servletRequest, servletResponse);
//            } catch (Exception e) {
//                log.error("filter 登陆放行doFilter失败" + e.getMessage());
//            }
//        }
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//}
