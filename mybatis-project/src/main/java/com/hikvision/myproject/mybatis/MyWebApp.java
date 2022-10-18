package com.hikvision.myproject.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Classname MyWebApp
 * @Description 启动类
 * @Date 2022/7/14 13:49
 * @Created by huangqiqi
 */
@MapperScan(value = "com.hikvision.myproject.mybatis.mapper")
@SpringBootApplication
public class MyWebApp {

    public static void main(String[] args) {
        SpringApplication.run(MyWebApp.class,args);
    }
}
