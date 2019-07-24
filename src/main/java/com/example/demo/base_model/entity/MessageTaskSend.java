package com.example.demo.base_model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 消息任务发送表(实现分布式事务最终一致性)
 */
@Data
@ToString
@TableName(value = "xc_task")
public class MessageTaskSend implements Serializable {

    @Id
    private String id;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     *消息最终发送成功删除时间
     */
    private Date deleteTime;
    /**
     * 任务类型
     */
    private String taskType;
    /**
     * 发送任务交换机名称
     */
    private String mqExchange;
    /**
     * 发送任务的routingKey名称
     */
    private String mqRoutingkey;
    /**
     * 发送内容
     */
    private String requestBody;
    /**
     * 发送版本号控制（发送次数）
     */
    private Integer version;
    /**
     * 状态
     */
    private String status;
    /**
     * 发送错误消息
     */
    private String errorMsg;
}
