package com.example.kafka3.controller;

import com.example.kafka3.config.MessageSenderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname KafkaController
 * @Description TODO
 * @Date 2022/8/18 10:47
 * @Created by huangqiqi
 */
@RestController
public class KafkaController {
    @Autowired
    MessageSenderClient messageSenderClient;

    @GetMapping("/send")
    public void send(){
        messageSenderClient.send("test","这是一条测试数据");
    }
}
