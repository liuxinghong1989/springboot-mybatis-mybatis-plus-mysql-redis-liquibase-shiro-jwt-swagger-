package com.example.demo.jms_rabbitmq;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.io.IOException;

/**
 * @author liuxinghong
 * @Description:  消息队列监听类
 * @date 2019/6/19 001911:06
 */
@Slf4j
@Component
public class RabbitQueueListener {

    /**
     * 普通队列监听
     * @param message
     * @param channel
     */
    @RabbitListener(queues = {"${rabbitmq.msg-send-queue}"})
        public void readMsg(Message message, Channel channel){
        try {
            log.info("接收到的消息数据》》》："+JSONObject.toJSONString(new String(message.getBody())));
            //此处处理逻辑业务操作
        }catch (Exception e){
            log.error(e.getMessage());
            try {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), true, true);
            } catch (IOException e1) {
                log.error(e.getMessage());
                e.printStackTrace();
            }
        }
        finally {
            //最终确认消息已消费（慎用次操作，操作后消息将不复存在）
            try {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
            } catch (IOException e) {
                log.error(e.getMessage());
                e.printStackTrace();
            }

        }
    }

    /**
     * 延时队列监听
     * @param message
     * @param channel
     */
    @RabbitListener(queues = {"${rabbitmq.delayed-queue-name}"})
    public void readDelayedMsg(Message message, Channel channel){
        try {
            log.info("接收到的延时消息数据》》》："+JSONObject.toJSONString(new String(message.getBody())));
            //此处处理逻辑业务操作
        }catch (Exception e){
            log.error(e.getMessage());
            try {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), true, true);
            } catch (IOException e1) {
                log.error(e.getMessage());
                e.printStackTrace();
            }
        }
        finally {
            //最终确认消息已消费（慎用次操作，操作后消息将不复存在）
            try {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
            } catch (IOException e) {
                log.error(e.getMessage());
                e.printStackTrace();
            }

        }
    }


}
