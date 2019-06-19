package com.example.demo.jms_rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 延迟队列和普通队列创建与绑定
 */
@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.delayed-queue-name}")
    private String delayedQueueName;

    @Value("${rabbitmq.delayed-exchange-name}")
    private String delayedExchange;

    @Value("${rabbitmq.msg-send-queue}")
    private String msgSendQueueName;

    @Value("${rabbitmq.msg-send-exchange}")
    private String msgSendExchangeName;

    private static final String X_DELAYED_TYPE = "x-delayed-type";

    /**
     * 延迟队列交换机定义
     * @return 返回交换机
     */
    @Bean
    public Exchange delayedExchange() {
        return ExchangeBuilder.directExchange(delayedExchange)
                .delayed()//延时设置
                .durable(true)
                .withArgument(X_DELAYED_TYPE, "direct")//延时插件配置
                .build();
    }

    /**
     * 延迟队列创建
     * @return 车辆白名单队列
     */
    @Bean
    public Queue delayedQueue() {
        return QueueBuilder
                .durable(delayedQueueName)
                .build();
    }

    /**
     * 延迟队列交换机与队列绑定
     * @param delayedExchange 交换机
     * @param delayedQueue 队列
     * @return 返回绑定对象
     */
    @Bean
    public Binding whiteExchangeQueueBinding(Exchange delayedExchange, Queue delayedQueue) {
        return BindingBuilder.bind(delayedQueue).to(delayedExchange).with(delayedQueueName).noargs();
    }


    /**
     * =========================================================
     *                消息推送队列创建（普通队列）
     * =========================================================
     */
    @Bean
    public Exchange msgExchange() {
        return ExchangeBuilder.directExchange(msgSendExchangeName).durable(true).build();
    }
    /**
     * 创建短信、邮件和app推送队列
     * @return 返回Queue
     */
    @Bean
    public Queue msgSendQueue() {
        return QueueBuilder.durable(msgSendQueueName).build();
    }

    @Bean
    public Binding msgBinding(Exchange msgExchange, Queue msgSendQueue) {
        return BindingBuilder.bind(msgSendQueue).to(msgExchange).with(msgSendQueueName).noargs();
    }
}
