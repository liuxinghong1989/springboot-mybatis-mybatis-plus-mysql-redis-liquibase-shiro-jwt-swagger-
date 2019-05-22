package com.example.demo.base_controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.commons.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 基础Controller
 * @author lxh
 */
public class BaseController {


    private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    private static final String TOKEN_NON_EXISTENT = "Missing cookie 'token'";

    /** 设置成功响应 */
    protected ResponseEntity<Object> success(Object data) {
        return responseEntity(HttpStatus.OK,data,HttpStatus.OK.msg());
    }

    protected ResponseEntity<Object> success(String msg) {
        return responseEntity(HttpStatus.OK,null,msg);
    }

    protected ResponseEntity<Object> success() {
        return responseEntity(HttpStatus.OK,null,HttpStatus.OK.msg());
    }

    protected ResponseEntity<Object> success(Object data, String msg) {
        return responseEntity(HttpStatus.OK,data,msg);
    }

    /** 设置失败响应 */
    protected ResponseEntity<Object> error(Object data) {
        return responseEntity(HttpStatus.BAD_REQUEST,data,HttpStatus.BAD_REQUEST.msg());
    }

    protected ResponseEntity<Object> error(String msg) {
        return responseEntity(HttpStatus.BAD_REQUEST,null,msg);
    }

    protected ResponseEntity<Object> error(HttpStatus code ){
        return responseEntity(code,null,code.msg());
    }

    protected ResponseEntity<Object> error() {
        return responseEntity(HttpStatus.BAD_REQUEST,null,HttpStatus.BAD_REQUEST.msg());
    }

    protected ResponseEntity<Object> error(Object data, String msg) {
        return responseEntity(HttpStatus.BAD_REQUEST,data,msg);
    }

    /** 设置响应代码 */
    protected ResponseEntity<Object> responseEntity(HttpStatus code, Object data, String msg) {
        Map<String,Object> map = Maps.newHashMap();
        if (data != null) {
            if (data instanceof Page) {
                Page<?> page = (Page<?>) data;
                map.put("data", page.getRecords());
                map.put("current", page.getCurrent());
                map.put("size", page.getSize());
                map.put("pages", page.getPages());
                map.put("total", page.getTotal());
            } else if (data instanceof List<?>) {
                map.put("data", data);
            } else {
                map.put("data", data);
            }
        }
        map.put("code", code.value());
        map.put("msg", msg);
        map.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.ok()
                .header("Access-Control-Allow-Origin","*")
                .contentType(MediaType.APPLICATION_JSON)
                .body(map);
    }

    /** 异常处理 */
    @ExceptionHandler(Exception.class)
    public void exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex)
            throws Exception {
        Map<String,Object> map = Maps.newHashMap();
        int status = 0;
        // 方法级别shiro权限校验失败时异常信息处理
        if(ex instanceof MethodArgumentNotValidException){
            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            map.put("code", HttpStatus.VALIDATED_FAIL.value());
            MethodArgumentNotValidException mane = (MethodArgumentNotValidException)ex;
            BindingResult bindingResult = mane.getBindingResult();
            List<String> errorMessage = Lists.newArrayList();
            List<FieldError> fes = bindingResult.getFieldErrors();
            for (FieldError fe : fes) {
                errorMessage.add(fe.getDefaultMessage());
            }
            map.put("msg", Joiner.on(",").join(errorMessage));
        } else if(ex instanceof ServletRequestBindingException) {
            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            if(StringUtils.contains(ex.getMessage(),TOKEN_NON_EXISTENT)) {
                status = HttpStatus.UNAUTHORIZED.value();
                map.put("code", HttpStatus.UNAUTHORIZED.value());
                String message = StringUtils.isEmpty(ex.getMessage()) ? ex.getCause().getMessage() : ex.getMessage();
                map.put("msg",message);
            }
        }else  if(ex instanceof UnauthorizedException) {//登录权限异常
            status = HttpStatus.UNAUTHORIZED.value();
            map.put("code", HttpStatus.UNAUTHORIZED.value());
            String message = StringUtils.isEmpty(ex.getMessage()) ? ex.getCause().getMessage() : ex.getMessage();
            map.put("msg",message);
        }else  if(ex instanceof RuntimeException) {
            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            map.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
            String message = StringUtils.isEmpty(ex.getMessage()) ? ex.getCause().getMessage() : ex.getMessage();
            map.put("msg",message);
        }


        if(status == 0) {
            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            map.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("msg", HttpStatus.INTERNAL_SERVER_ERROR.msg());
        }
        ex.printStackTrace();
        LOGGER.error(ex.getMessage(),ex);
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setStatus(status);
        map.put("timestamp", System.currentTimeMillis());
        response.getOutputStream().write(new ObjectMapper().writeValueAsString(map).getBytes());
    }

}
