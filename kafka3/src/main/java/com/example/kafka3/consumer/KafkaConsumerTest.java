package com.example.kafka3.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * kafka 监听者
 */
@Slf4j
@Component
public class KafkaConsumerTest {

    @KafkaListener(topics = {"test"})
    void onMessage1(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        // 消费的哪个topic、partition的消息,打印出消息内容
        System.out.println("kafka consumer：" + record.topic() + "-" + record.partition() + "-" + record.value());
        String value = record.value();
        System.out.println("value:"+value);

        /**
         * 业务处理代码。。。。。。
         */

        //处理完成后，手动提交ack
        acknowledgment.acknowledge();
    }

}

