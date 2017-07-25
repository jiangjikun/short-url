package com.spiderdt.common;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan("com.spiderdt.common.dao")
@EnableConfigurationProperties
//@EnableAsync
public class UrlApplication {

    public static void main(String[] args) {
        SpringApplication.run(UrlApplication.class, args);
    }
}
