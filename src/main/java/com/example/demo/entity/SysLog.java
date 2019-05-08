package com.example.demo.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
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
public class SysLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日志信息表主键
     */
	private Long id;
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


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAdr() {
		return adr;
	}

	public void setAdr(String adr) {
		this.adr = adr;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	@Override
	public String toString() {
		return "SysLog{" +
			", id=" + id +
			", type=" + type +
			", desc=" + desc +
			", remark=" + remark +
			", adr=" + adr +
			", status=" + status +
			", createTime=" + createTime +
			", createUser=" + createUser +
			"}";
	}
}
