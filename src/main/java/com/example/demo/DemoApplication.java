package com.example.demo;
import com.example.demo.jms_rabbitmq.servicce.RabbitMQSendService;
import com.example.demo.jms_rabbitmq.servicce.RabbitMQSendServiceImpl;
import org.joda.time.DateTime;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
@MapperScan("com.example.demo.mapper")
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
