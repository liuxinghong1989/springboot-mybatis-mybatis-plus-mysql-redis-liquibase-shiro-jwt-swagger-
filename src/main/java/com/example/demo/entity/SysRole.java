package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author LXH
 * @since 2019-05-06
 */
@TableName("sys_role")
@Data
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private String code;
	@TableField("sort_num")
	private Integer sortNum;
	private String des;
    /**
     * 角色类型  0，超管 1.普通用户
     */
	private Integer type;
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



}
