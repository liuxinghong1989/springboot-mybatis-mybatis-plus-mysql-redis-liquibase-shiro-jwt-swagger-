package com.example.demo.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author LXH
 * @since 2019-05-06
 */
@TableName("sys_user")
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

	private String id;
	@TableField("login_name")
	private String loginName;
    @TableField("user_no")
    private String userNo;
	private String pwd;
	private String salt;
	private String name;
	private String email;
    /**
     * 1:男，0:女
     */
	private Integer sex;
    /**
     * 手机号
     */
    @TableField("mobile_no")
    private String mobileNo;
    /**
     * 微信号
     */
    @TableField("weixin_id")
    private String weixinId;
    /**
     * 注册时间
     */
    @TableField("reg_time")
    private Date regTime;
    /**
     * 头像
     */
	private String avatar;
    /**
     * 1 可用 0 不可用 -1: 删除
     */
	private Integer status;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;
    /**
     * 修改时间
     */
	@TableField("update_time")
	private Date updateTime;
    /**
     * 客户id
     */
    @TableField("client_id")
    private String clientId;

}
