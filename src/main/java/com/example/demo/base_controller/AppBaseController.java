package com.example.demo.base_controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.commons.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
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
public class AppBaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppBaseController.class);


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
        /*if (ex instanceof AuthorizationException){
            status = HttpStatus.FORBIDDEN.value();
            map.put("code", HttpStatus.FORBIDDEN.value());
            map.put("msg", HttpStatus.FORBIDDEN.msg());
        } else if (ex instanceof UnauthorizedException) {
            status = HttpStatus.FORBIDDEN.value();
            map.put("code", HttpStatus.FORBIDDEN.value());
            map.put("msg", HttpStatus.FORBIDDEN.msg());
        } else if(ex instanceof LoginException){
            status = HttpStatus.FORBIDDEN.value();
            map.put("code", HttpStatus.LOGIN_FAIL.value());
            map.put("msg", ex.getMessage());
        } else if(ex instanceof UploadException){
            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            map.put("code", HttpStatus.V5_UPLOAD_FAIL.value());
            map.put("msg", ex.getMessage());
        } else if(ex instanceof TokenParserException) {
            status = HttpStatus.FORBIDDEN.value();
            map.put("code", HttpStatus.TOKEN_PARSER_FAIL.value());
            map.put("msg", ex.getMessage());*/
         if(ex instanceof MethodArgumentNotValidException){
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
            if(StringUtils.contains(ex.getMessage(),TOKEN_NON_EXISTENT)) {
                map.put("code", HttpStatus.UNAUTHORIZED.value());
                map.put("msg",ex.getMessage());
            }
        } else if(ex instanceof RuntimeException) {
            map.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("msg",ex.getMessage());
        } else {
            map.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("msg", HttpStatus.INTERNAL_SERVER_ERROR.msg());
        }

        ex.printStackTrace();
        LOGGER.error(ex.getMessage(),ex);
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setStatus(200);
        map.put("timestamp", System.currentTimeMillis());
        response.getOutputStream().write(new ObjectMapper().writeValueAsString(map).getBytes());
    }

}
