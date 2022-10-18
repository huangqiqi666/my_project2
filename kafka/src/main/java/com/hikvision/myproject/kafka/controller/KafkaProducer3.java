//package com.hikvision.myproject.kafka.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * @Classname KafkaProducer3
// * @Description kafka生产者（带事务）
// * @Date 2022/8/15 16:20
// * @Created by huangqiqi
// */
//@RestController
//public class KafkaProducer3 {
//    @Autowired
//    private KafkaTemplate<String, Object> kafkaTemplate;
//
//    @GetMapping("/kafka/transaction")
//    public void sendMessage7(){
//        // 声明事务：后面报错消息不会发出去
//        kafkaTemplate.executeInTransaction(operations -> {
//            operations.send("topic1","test executeInTransaction");
//            throw new RuntimeException("fail");
//        });
//
//        // 不声明事务：后面报错但前面消息已经发送成功了
//        kafkaTemplate.send("topic1","test executeInTransaction");
//        throw new RuntimeException("fail");
//    }
//}
