package com.example.demo.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author liuxinghong
 * @Description:
 * @date 2019/5/6 000618:43
 */
@Configuration
public class LiquibaseConfig {

    @Value("${liquibase.path}")
    private String path;
    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(path);
      //  liquibase.setContexts("dev,test,production");
        liquibase.setShouldRun(true);
        return liquibase;
    }

}
