package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 资源表
 * </p>
 *
 * @author LXH
 * @since 2019-05-06
 */
@TableName("sys_res")
public class SysRes implements Serializable {

    private static final long serialVersionUID = 1L;

	private Long id;
	private Long pid;
    /**
     * 在vue中name表示路由名称，非vue是资源名称
     */
	private String name;
	private String permission;
    /**
     * vue中表示路由的path
     */
	private String url;
    /**
     * 排序
     */
	@TableField("sort_num")
	private Integer sortNum;
    /**
     * PC菜单图片
     */
	private String icon1;
    /**
     * APP菜单图片
     */
	private String icon2;
    /**
     * TreeTable排序
     */
	private String pids;
    /**
     * 1 菜单 2 按钮
     */
	private Integer type;
	private String des;
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

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	public String getIcon1() {
		return icon1;
	}

	public void setIcon1(String icon1) {
		this.icon1 = icon1;
	}

	public String getIcon2() {
		return icon2;
	}

	public void setIcon2(String icon2) {
		this.icon2 = icon2;
	}

	public String getPids() {
		return pids;
	}

	public void setPids(String pids) {
		this.pids = pids;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
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
		return "SysRes{" +
			", id=" + id +
			", pid=" + pid +
			", name=" + name +
			", permission=" + permission +
			", url=" + url +
			", sortNum=" + sortNum +
			", icon1=" + icon1 +
			", icon2=" + icon2 +
			", pids=" + pids +
			", type=" + type +
			", des=" + des +
			", status=" + status +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			"}";
	}
}
