package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 资源表
 * </p>
 *
 * @author LXH
 * @since 2019-05-06
 */
@TableName("sys_res")
@Data
public class SysRes implements Serializable {

    private static final long serialVersionUID = 1L;

	private String id;
	private String pid;
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

    @TableField(exist = false)
    private List<SysRes> ChildRes;



}
