package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

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
@Data
@Builder
public class SysRoleRes implements Serializable {

    private static final long serialVersionUID = 1L;

	private String id;
	@TableField("res_id")
	private String resId;
	@TableField("role_id")
	private String roleId;

}
