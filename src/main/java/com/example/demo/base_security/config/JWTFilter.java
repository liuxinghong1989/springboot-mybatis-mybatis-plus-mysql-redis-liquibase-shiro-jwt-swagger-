package com.example.demo.base_security.config;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.base_security.commons.Constant;
import com.example.demo.entity.SysUser;
import com.example.demo.base_security.commons.PublicResultConstant;
import com.example.demo.service.SysUserService;
import com.example.demo.base_security.util.ComUtil;
import com.example.demo.base_security.util.JWTUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author grm
 *
 * 代码的执行流程preHandle->isAccessAllowed->isLoginAttempt->executeLogin
 */
public class JWTFilter extends BasicHttpAuthenticationFilter{

    private SysUserService userService;
    /**
     * 判断用户是否想要登入。
     * 检测header里面是否包含Authorization字段即可
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader("Authorization");
        return authorization != null;
    }

    /**
     *
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader("Authorization");
        JWTToken token = new JWTToken(authorization);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(token);
        // 如果没有抛出异常则代表登入成功，返回true
        setUserBean(request, response, token);
        return true;
    }

    /**
     * 这里我们详细说明下为什么最终返回的都是true，即允许访问
     * 例如我们提供一个地址 GET /article
     * 登入用户和游客看到的内容是不同的
     * 如果在这里返回了false，请求会被直接拦截，用户看不到任何东西
     * 所以我们在这里返回true，Controller中可以通过 subject.isAuthenticated() 来判断用户是否登入
     * 如果有些资源只有登入用户才能访问，我们只需要在方法上面加上 @RequiresAuthentication 注解即可
     * 但是这样做有一个缺点，就是不能够对GET,POST等请求进行分别过滤鉴权(因为我们重写了官方的方法)，但实际上对应用影响不大
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request, response)) {
            try {
                executeLogin(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                responseError(request, response);
            }
        }
        return true;
    }

    private void setUserBean(ServletRequest request, ServletResponse response, JWTToken token) {
        if (this.userService == null) {
            this.userService = SpringContextBeanService.getBean(SysUserService.class);
        }
        String userId =  JWTUtil.getUserNo(token.getPrincipal().toString());
        SysUser userBean = userService.getById(userId);
        request.setAttribute("currentUser", userBean);
    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        String authorization = httpServletRequest.getHeader("Authorization");
        if (verificationPassAnnotation(request, response, httpServletRequest, authorization)){
            return true;
        }
        if(ComUtil.isEmpty(authorization)){
            responseError(request, response);
            return false;
        }
        return super.preHandle(request, response);
    }

    /**
     * 验证请求方法是否有@Pass注解,有则直接放行
     * @param request
     * @param response
     * @param httpServletRequest
     * @param authorization
     * @return
     * @throws Exception
     */
    private boolean verificationPassAnnotation(ServletRequest request, ServletResponse response, HttpServletRequest httpServletRequest, String authorization) throws Exception {
        for (String urlMethod: Constant.METHOD_URL_SET) {
            String[] split = urlMethod.split(":--:");
            if(split[0].equals(httpServletRequest.getRequestURI())
                    && (split[1].equals(httpServletRequest.getMethod()) ||  split[1].equals("RequestMapping"))){
                Constant.isPass=true;
                if(ComUtil.isEmpty(authorization)){
                    //如果当前url不需要认证，则注入当前登录用户时，给一个空的
                    httpServletRequest.setAttribute("currentUser",new SysUser());
                    return true;
                }else {
                    super.preHandle(request, response);
                }
            }
            if(StringUtils.countMatches(urlMethod, "{")>0 &&
                    StringUtils.countMatches(urlMethod, "/") == StringUtils.countMatches(split[0], "/")
                    && (split[1].equals(httpServletRequest.getMethod()) ||  split[1].equals("RequestMapping"))){
                if(isSameUrl(split[0],httpServletRequest.getRequestURI())){
                    Constant.isPass=true;
                    if(ComUtil.isEmpty(authorization)){
                        httpServletRequest.setAttribute("currentUser",new SysUser());
                        return true;
                    }else {
                        super.preHandle(request, response);
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断路径参数的url是否和controller方法url一致
     * @param localUrl
     * @param requestUrl
     * @return
     */
    private boolean isSameUrl(String localUrl,String requestUrl){
        String[] tempLocalUrls = localUrl.split("/");
        String[] tempRequestUrls = requestUrl.split("/");
        if(tempLocalUrls.length != tempRequestUrls.length){
            return false;
        }
        StringBuilder sbLocalUrl =new StringBuilder();
        StringBuilder sbRequestUrl =new StringBuilder();
        for (int i = 0; i < tempLocalUrls.length; i++) {
            if(StringUtils.countMatches(tempLocalUrls[i], "{") > 0){
                tempLocalUrls[i]="*";
                tempRequestUrls[i]="*";
            }
            sbLocalUrl.append(tempLocalUrls[i]+"/");
            sbRequestUrl.append(tempRequestUrls[i]+"/");
        }
        return sbLocalUrl.toString().trim().equals(sbRequestUrl.toString().trim());
    }

    /**
     * 非法url返回身份错误信息
     */
    private void responseError(ServletRequest request, ServletResponse response) {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("utf-8");
            out = response.getWriter();
            response.setContentType("application/json; charset=utf-8");
            out.print(JSONObject.toJSONString(ResponseHelper.unauthorized(PublicResultConstant.UNAUTHORIZED)));//401
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
