package com.example.demo.base_security.VO;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel("注册参数")
public class registerVO implements Serializable {

    /**
     * 用户名
     */
    @NotNull(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名")
    private String loginName;
    /**
     * 用户编号
     */
    @ApiModelProperty(value = "用户编号")
    private String userNo;
    /**
     * 用户密码
     */
    @NotNull(message = "密码不能为空")
    @ApiModelProperty(value = "密码")
    private String pwd;

    private String salt;
    /**
     * 姓名
     */
    @NotNull(message = "名称不能为空")
    @ApiModelProperty(value = "名称")
    private String name;
    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;
    /**
     * 1:男，0:女
     */
    @ApiModelProperty(value = "性别")
    private Integer sex;
    /**
     * 手机号
     */
    @NotNull(message = "手机号不能为空")
    @Pattern(regexp = "^1[345789]\\d{9}$",message = "手机号格式不正确！")
    @ApiModelProperty(value = "手机号")
    private String mobileNo;
    /**
     * 微信号
     */
    @ApiModelProperty(value = "微信号")
    private String weixinId;
    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String avatar;

    /**
     * 验证码
     */
    @ApiModelProperty(value = "验证码")
    private String captcha;
}
