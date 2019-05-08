package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 * 角色资源表
 * </p>
 *
 * @author LXH
 * @since 2019-05-06
 */
@TableName("sys_role_res")
public class SysRoleRes implements Serializable {

    private static final long serialVersionUID = 1L;

	private Long id;
	@TableField("res_id")
	private Long resId;
	@TableField("role_id")
	private Long roleId;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getResId() {
		return resId;
	}

	public void setResId(Long resId) {
		this.resId = resId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return "SysRoleRes{" +
			", id=" + id +
			", resId=" + resId +
			", roleId=" + roleId +
			"}";
	}
}
