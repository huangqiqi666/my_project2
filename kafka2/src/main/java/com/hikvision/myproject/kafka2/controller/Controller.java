package com.hikvision.myproject.kafka2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname Controller
 * @Description TODO
 * @Date 2022/8/17 11:03
 * @Created by huangqiqi
 */
@RestController
public class Controller {
    @Autowired
    private KafkaTemplate kafkaTemplate;


    @GetMapping("producer")
    public void producer() throws InterruptedException {
        kafkaTemplate.send("test", "this is my first demo");
        Thread.sleep(5000);
    }
}
