package com.hikvision.myproject.kafka2.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MsgProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    
    public void send(String value){
        System.out.println("send start-----------");
        kafkaTemplate.send("test", value+"1");
        kafkaTemplate.send("test", "key1", value+"2");
        System.out.println("send end-----------");
    }
}