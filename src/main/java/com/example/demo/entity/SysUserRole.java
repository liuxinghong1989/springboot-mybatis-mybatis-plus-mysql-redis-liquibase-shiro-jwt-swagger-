package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 用户角色表
 * </p>
 *
 * @author LXH
 * @since 2019-05-06
 */
@TableName("sys_user_role")
@Data
public class SysUserRole implements Serializable {

    private static final long serialVersionUID = 1L;

	private String id;
	@TableField("user_id")
	private String userId;
	@TableField("role_id")
	private String roleId;



}
