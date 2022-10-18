package com.hikvision.myproject.kafka2.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class MsgConsumer {
    static Logger logger = LoggerFactory.getLogger("MsgConsumer");

    //分组1
    @KafkaListener(topics="test",containerFactory="kafkaListenerContainerFactory")
    public void processMsg(ConsumerRecord<?, ?> record){
        logger.info("-----kafka接收到消息：{}|{}|{}|{}",record.topic(),record.partition(),record.offset(),record.value());
    }

    //分组2
    @KafkaListener(topics="test",containerFactory="kafkaListenerContainerFactory1")
    public void processMsg1(ConsumerRecord<?, ?> record){
        logger.info("-----kafka接收到消息{}|{}|{}|{}",record.topic(),record.partition(),record.offset(),record.value());
    }
    
}