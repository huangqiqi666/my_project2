package com.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@MapperScan(basePackages = {"com.test.mapper"})//扫描mapper
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class KingbaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(KingbaseApplication.class, args);
    }
}

