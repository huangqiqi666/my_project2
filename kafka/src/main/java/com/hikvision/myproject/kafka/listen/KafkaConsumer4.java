//package com.hikvision.myproject.kafka.listen;
//
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.stereotype.Component;
//
///**
// * @Classname KafkaConsumer4
// * @Description todo：消息转发
// * 从topic1接收到的消息经过处理后转发到topic2
// * 在SpringBoot集成Kafka实现消息的转发也很简单，只需要通过一个@SendTo注解，被注解方法的return值即转发的消息内容
// * @Date 2022/8/15 16:04
// * @Created by huangqiqi
// */
//@ConditionalOnMissingBean(KafkaConsumer.class)
//@Component
//public class KafkaConsumer4 {
//
//    /**
//     * @Title 消息转发
//     * @Description 从topic1接收到的消息经过处理后转发到topic2
//     * @Author long.yuan
//     * @Date 2020/3/23 22:15
//     * @Param [record]
//     * @return void
//     **/
//    @KafkaListener(topics = {"topic1"})
//    @SendTo("topic2")
//    public String onMessage7(ConsumerRecord<?, ?> record) {
//        return record.value()+"-forward message";
//    }
//}
