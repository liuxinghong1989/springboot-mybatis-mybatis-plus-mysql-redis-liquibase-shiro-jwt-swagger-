package com.example.demo.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import scala.util.parsing.combinator.testing.Str;

import java.io.Serializable;

/**
 * <p>
 * 日志信息表
 * </p>
 *
 * @author LXH
 * @since 2019-05-06
 */
@TableName("sys_log")
@Data
public class SysLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日志信息表主键
     */
	private String id;
    /**
     * 日志类型（1:增，2:删，3:改，4:查）
     */
	private Integer type;
    /**
     * 日志说明（日志数据）
     */
	private String desc;
    /**
     * 操作详情
     */
	private String remark;
    /**
     * 操作人IP地址
     */
	private String adr;
    /**
     * 删除标识（1:未删除，2:已删除）
     */
	private Integer status;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;
    /**
     * 创建用户
     */
	@TableField("create_user")
	private String createUser;



}
