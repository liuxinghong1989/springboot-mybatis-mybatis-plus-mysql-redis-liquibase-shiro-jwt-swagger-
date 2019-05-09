package com.example.demo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ServletComponentScan
@SpringBootApplication
@MapperScan("com.example.demo.mapper")
public class DemoApplication {

	public static void main(String[] args) {

	    SpringApplication.run(DemoApplication.class, args);

        String encode = new BCryptPasswordEncoder().encode("123456");
        System.out.println(encode);
    }

}
