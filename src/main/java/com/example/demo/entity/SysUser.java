package com.example.demo.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
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
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

	private Long id;
	@TableField("login_name")
	private String loginName;
	private String pwd;
	private String salt;
	private String name;
	private String email;
    /**
     * 1:男，0:女
     */
	private Integer sex;
    /**
     * 电话
     */
	private String telephone;
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


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "SysUser{" +
			", id=" + id +
			", loginName=" + loginName +
			", pwd=" + pwd +
			", salt=" + salt +
			", name=" + name +
			", email=" + email +
			", sex=" + sex +
			", telephone=" + telephone +
			", avatar=" + avatar +
			", status=" + status +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			"}";
	}
}
