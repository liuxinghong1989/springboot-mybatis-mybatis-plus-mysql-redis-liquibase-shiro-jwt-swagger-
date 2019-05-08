package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * app用户表
 * </p>
 *
 * @author LXH
 * @since 2019-05-06
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
	private Long id;
    /**
     * 用户编码
     */
	@TableField("user_no")
	private String userNo;
    /**
     * 手机号
     */
	@TableField("mobile_no")
	private String mobileNo;
    /**
     * 登录用户名
     */
	@TableField("login_id")
	private String loginId;
    /**
     * 登录密码
     */
	private String password;
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
     * 注册终端编码
     */
	@TableField("reg_term_id")
	private String regTermId;
    /**
     * 姓名
     */
	private String name;
    /**
     * 通信地址
     */
	private String address;
    /**
     * 电子邮箱
     */
	private String email;
    /**
     * 头像地址
     */
	@TableField("photo_addr")
	private String photoAddr;
    /**
     * 生日
     */
	private Date birthday;
    /**
     * 身份证号码
     */
	@TableField("idcard_no")
	private String idcardNo;
    /**
     * 驾驶证号码
     */
	@TableField("driver_no")
	private String driverNo;
    /**
     * 驾驶证图片-正本
     */
	@TableField("driver_img1")
	private String driverImg1;
    /**
     * 驾驶证图片-副本
     */
	@TableField("driver_img2")
	private String driverImg2;
    /**
     * 积分数
     */
	private Integer integral;
    /**
     * 短信推送标志. 0-关闭  1-打开
     */
	@TableField("sms_push_flag")
	private Integer smsPushFlag;
    /**
     * 无感支付开关. 0-关闭  1-打开
     */
	@TableField("nocensce_flag")
	private Integer nocensceFlag;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;
    /**
     * 更新时间
     */
	@TableField("update_time")
	private Date updateTime;
    /**
     * 状态，1 未删除，0 禁用，-1 删除，2 启用
     */
	private Integer status;
    /**
     * 客户id
     */
	@TableField("client_id")
	private Long clientId;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getWeixinId() {
		return weixinId;
	}

	public void setWeixinId(String weixinId) {
		this.weixinId = weixinId;
	}

	public Date getRegTime() {
		return regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	public String getRegTermId() {
		return regTermId;
	}

	public void setRegTermId(String regTermId) {
		this.regTermId = regTermId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhotoAddr() {
		return photoAddr;
	}

	public void setPhotoAddr(String photoAddr) {
		this.photoAddr = photoAddr;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getIdcardNo() {
		return idcardNo;
	}

	public void setIdcardNo(String idcardNo) {
		this.idcardNo = idcardNo;
	}

	public String getDriverNo() {
		return driverNo;
	}

	public void setDriverNo(String driverNo) {
		this.driverNo = driverNo;
	}

	public String getDriverImg1() {
		return driverImg1;
	}

	public void setDriverImg1(String driverImg1) {
		this.driverImg1 = driverImg1;
	}

	public String getDriverImg2() {
		return driverImg2;
	}

	public void setDriverImg2(String driverImg2) {
		this.driverImg2 = driverImg2;
	}

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public Integer getSmsPushFlag() {
		return smsPushFlag;
	}

	public void setSmsPushFlag(Integer smsPushFlag) {
		this.smsPushFlag = smsPushFlag;
	}

	public Integer getNocensceFlag() {
		return nocensceFlag;
	}

	public void setNocensceFlag(Integer nocensceFlag) {
		this.nocensceFlag = nocensceFlag;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	@Override
	public String toString() {
		return "User{" +
			", id=" + id +
			", userNo=" + userNo +
			", mobileNo=" + mobileNo +
			", loginId=" + loginId +
			", password=" + password +
			", weixinId=" + weixinId +
			", regTime=" + regTime +
			", regTermId=" + regTermId +
			", name=" + name +
			", address=" + address +
			", email=" + email +
			", photoAddr=" + photoAddr +
			", birthday=" + birthday +
			", idcardNo=" + idcardNo +
			", driverNo=" + driverNo +
			", driverImg1=" + driverImg1 +
			", driverImg2=" + driverImg2 +
			", integral=" + integral +
			", smsPushFlag=" + smsPushFlag +
			", nocensceFlag=" + nocensceFlag +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			", status=" + status +
			", clientId=" + clientId +
			"}";
	}
}
