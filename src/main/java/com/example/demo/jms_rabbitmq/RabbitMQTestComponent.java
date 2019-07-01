package com.example.demo.jms_rabbitmq;

import com.example.demo.commons.Constants;
import com.example.demo.jms_rabbitmq.servicce.RabbitMQSendService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author liuxinghong
 * @Description: 项目启动后  执行该方法
 * @date 2019/6/20 002011:46
 */
@Component
public class RabbitMQTestComponent implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private  RabbitMQSendService rabbitMQSendService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        for (int i= 1; i<1000; i++){
            rabbitMQSendService.sendMessage("普通消息发送"+ DateTime.now().toString(Constants.YYYY_MM_DD_HH_MM_SS));
            rabbitMQSendService.sendDelayedMessage(30000,"延时消息发送"+ DateTime.now().toString(Constants.YYYY_MM_DD_HH_MM_SS));
        }
    }
}
