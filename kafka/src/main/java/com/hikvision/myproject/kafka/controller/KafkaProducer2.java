//package com.hikvision.myproject.kafka.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.support.SendResult;
//import org.springframework.util.concurrent.ListenableFutureCallback;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * @Classname KafkaProducer2
// * @Description kafka生产者（带回调函数）
// * @Date 2022/8/15 16:20
// * @Created by huangqiqi
// */
//@RestController
//public class KafkaProducer2 {
//    @Autowired
//    private KafkaTemplate<String, Object> kafkaTemplate;
//
//
//    //1、带回调的生产者1
//    @GetMapping("/kafka/callbackOne/{message}")
//    public void sendMessage2(@PathVariable("message") String callbackMessage) {
//        kafkaTemplate.send("topic1", callbackMessage).addCallback(
//                success -> {
//                    // 消息发送到的topic
//                    String topic = success.getRecordMetadata().topic();
//                    // 消息发送到的分区
//                    int partition = success.getRecordMetadata().partition();
//                    // 消息在分区内的offset
//                    long offset = success.getRecordMetadata().offset();
//                    System.out.println("发送消息成功:" + topic + "-" + partition + "-" + offset);
//                },
//                failure -> {
//                    System.out.println("发送消息失败:" + failure.getMessage());
//                });
//    }
//
//    //1、带回调的生产者2
//    @GetMapping("/kafka/callbackTwo/{message}")
//    public void sendMessage3(@PathVariable("message") String callbackMessage) {
//        kafkaTemplate.send("topic1", callbackMessage).addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
//            @Override
//            public void onFailure(Throwable ex) {
//                System.out.println("发送消息失败："+ex.getMessage());
//            }
//
//            @Override
//            public void onSuccess(SendResult<String, Object> result) {
//                System.out.println("发送消息成功：" + result.getRecordMetadata().topic() + "-"
//                        + result.getRecordMetadata().partition() + "-" + result.getRecordMetadata().offset());
//            }
//        });
//    }
//
//
//
//}