package com.example.demo.jms_rabbitmq.servicce;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author liuxinghong
 * @Description:
 * @date 2019/6/19 001911:29
 */
@Slf4j
@Service
public class RabbitMQSendServiceImpl implements RabbitMQSendService {

    @Value("${rabbitmq.delayed-queue-name}")
    private String delayedQueueName;

    @Value("${rabbitmq.delayed-exchange-name}")
    private String delayedExchange;

    @Value("${rabbitmq.msg-send-queue}")
    private String msgSendQueueName;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendDelayedMessage(Integer delayed, Object message) {
        try {
            MessageProperties properties = new MessageProperties();
            properties.setDelay(delayed);
            rabbitTemplate.send(delayedExchange,delayedQueueName,new Message(String.valueOf(message).getBytes(),properties));
            log.info("延迟消息发送成功》》》》》》》》      "+"消息内容："+message);
        }catch (Exception e){
          log.error("消息发送失败，错误日志： "+e.getStackTrace());
        }
    }

    @Override
    public void sendMessage(Object message) {

        try {
            rabbitTemplate.convertAndSend(msgSendQueueName,String.valueOf(message));
            log.info("消息发送成功》》》》》》》》      "+"消息内容："+message);
        }catch (Exception e){
            log.error("消息发送失败，错误日志： "+e.getStackTrace());
        }
    }


}
