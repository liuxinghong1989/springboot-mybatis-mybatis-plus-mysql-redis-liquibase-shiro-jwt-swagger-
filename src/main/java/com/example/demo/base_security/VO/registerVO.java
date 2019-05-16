package com.example.demo.base_security.VO;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author liuxinghong
 * @Description: 注册接口
 * @date 2019/5/15 001517:09
 */
@Data
public class registerVO implements Serializable {

    /**
     * 用户名
     */
    @NotNull(message = "用户名不能为空")
    private String loginName;
    /**
     * 用户编号
     */
    private String userNo;
    /**
     * 用户密码
     */
    @NotNull(message = "密码不能为空")
    private String pwd;

    private String salt;
    /**
     * 姓名
     */
    @NotNull(message = "名称不能为空")
    private String name;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 1:男，0:女
     */
    private Integer sex;
    /**
     * 手机号
     */
    @NotNull(message = "手机号不能为空")
    @Pattern(regexp = "^1[345789]\\d{9}$",message = "手机号格式不正确！")
    private String mobileNo;
    /**
     * 微信号
     */
    private String weixinId;
    /**
     * 头像
     */
    private String avatar;

    /**
     * 验证码
     */
    private String captcha;
}
