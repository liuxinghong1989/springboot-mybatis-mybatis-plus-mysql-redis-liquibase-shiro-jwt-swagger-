/**
 * Copyright © 2019, LeonKeh
 * <p>
 * All Rights Reserved.
 */

package com.example.demo.base_model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 消息
 *
 * @author lxh
 * @version 2019/4/17 10:15
 */
@Data
@TableName("message")
public class Message implements Serializable {
    /**
     * 主键
     */
    private String id;

    /**
     * 用户id
     */
    private String userId;
    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息内容
     */
    private String msg;

    /**
     * 业务数据
     */
    private String extra;

    /**
     * 推送状态
     * @see
     */
    private Integer status;

    /**
     * 消息类别
     * 消息分类：1,app消息  2，web端消息
     */
    private String category;

    /**
     * 业务链接
     */
    private String url;
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
