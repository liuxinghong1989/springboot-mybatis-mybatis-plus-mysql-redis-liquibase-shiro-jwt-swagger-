package com.example.demo.jms_rabbitmq.servicce;

/**
 * @author liuxinghong
 * @Description: 发送rabbitMq消息
 * @date 2019/6/19 001911:22
 */
public interface RabbitMQSendService {


    /**
     * 发送延迟队列
     */
    public void sendDelayedMessage(Integer delayed,Object message);

    /**
     * 发送普通消息队列
     * @param message
     */
    public void  sendMessage(Object message);
}
