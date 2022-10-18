package com.test.kingbasedemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = {"com.test.kingbasedemo.mapper"})
@SpringBootApplication
public class KingbaseDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(KingbaseDemoApplication.class, args);
    }

}