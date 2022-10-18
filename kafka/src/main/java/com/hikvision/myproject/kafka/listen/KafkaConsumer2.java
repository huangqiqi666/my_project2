//package com.hikvision.myproject.kafka.listen;
//
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.apache.kafka.clients.consumer.KafkaConsumer;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
///**
// * @Classname KafkaConsumer2
// * @Description todo:异常处理器的使用
// * 通过异常处理器，我们可以处理consumer在消费时发生的异常。
// * 新建一个 ConsumerAwareListenerErrorHandler 类型的异常处理方法，用@Bean注入，BeanName默认就是方法名，然后我们将这个异常处理器的BeanName放到@KafkaListener注解的errorHandler属性里面，当监听抛出异常的时候，则会自动调用异常处理器
// * @Date 2022/8/15 15:55
// * @Created by huangqiqi
// */
//@Component
//@ConditionalOnMissingBean(KafkaConsumer.class)
//public class KafkaConsumer2 {
//
//    // 新建一个异常处理器，用@Bean注入
//    @Bean
//    public ConsumerAwareListenerErrorHandler consumerAwareErrorHandler() {
//        return (message, exception, consumer) -> {
//            System.out.println("消费异常："+message.getPayload());
//            return null;
//        };
//    }
//
//    // 将这个异常处理器的BeanName放到@KafkaListener注解的errorHandler属性里面
//    @KafkaListener(topics = {"topic1"},errorHandler = "consumerAwareErrorHandler")
//    public void onMessage4(ConsumerRecord<?, ?> record) throws Exception {
//        throw new Exception("简单消费-模拟异常");
//    }
//
//    // 批量消费也一样，异常处理器的message.getPayload()也可以拿到各条消息的信息
//    @KafkaListener(topics = "topic1",errorHandler="consumerAwareErrorHandler")
//    public void onMessage5(List<ConsumerRecord<?, ?>> records) throws Exception {
//        System.out.println("批量消费一次...");
//        throw new Exception("批量消费-模拟异常");
//    }
//}
