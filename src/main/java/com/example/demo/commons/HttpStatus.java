package com.example.demo.commons;

/**
 * @author lxh
 */

public enum HttpStatus {

    OK(200, "请求成功"),
    BAD_REQUEST(400, "请求出错"),
    UNAUTHORIZED(401, "登录异常，重新登录"),
    FORBIDDEN(403, "没有权限"),
    NOT_FOUND(404, "找不到页面"),
    INTERNAL_SERVER_ERROR(500, "服务器出错"),
    LOGIN_FAIL(1001,"登录异常！"),
    TOKEN_PARSER_FAIL(1002,"令牌解析异常！"),
    VALIDATED_FAIL(1004,"数据验证失败！"),

    NSOP_DATA_NULL(501, "没有查询到数据"),
    NSOP_SAVE_FAIL(502, "保存数据失败"),
    NSOP_DEL_FAIL(503, "删除数据失败"),

    //用户已签约
    NSOP_CONTRACT(2008,"用户已签约！"),
    NSOP_NO_QUERY_CONTRACT(2009,"没有查询到签约信息！"),

    NSOP_CAPTCHA_NULL(601,"请输入验证码！"),
    NSOP_CAPTCHA_ERROR(602,"验证码输入错误！"),
    NSOP_CAPTCHA_INVALID(603,"验证码已失效！"),
    NSOP_UPLOAD_FAIL(1003,"上传失败");


    private final int value;

    private final String msg;

    HttpStatus(int value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    public int value() {
        return value;
    }

    public String msg() {
        return msg;
    }
}
