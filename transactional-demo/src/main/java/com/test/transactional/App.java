package com.test.transactional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @Classname App
 * @Description TODO
 * @Date 2022/10/17 9:50
 * @Created by huangqiqi
 */
@EnableAspectJAutoProxy(exposeProxy=true)//开启“代理暴露”，代理类就会被设置到AopContext中
@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class,args);
    }
}
