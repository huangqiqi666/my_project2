package com.example.kafka3.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.Future;

/**
 * kafka 配置
 *   由于kafka服务端使用的是 0.10.2版本，而我们的框架使用的spring boot版本为2.5.0，
 *   kafka starter版本中kafka-clients版本为2.7.1；kafka客户端作为生产者发送消息
 *   时，会增加请求头，而服务端不支持，导致无法正常生产消息，故kafka作为生产者，不能直
 *   接使用boot starter，需使用kafka原生生产者;
 */
@Slf4j
@Component
public class MessageSenderClient {


    @Resource
    private KafkaProducer kafkaProducer;

    /**
     * 消息发送至 kafka
     *
     * @param topic 路由
     * @param data 消息内容
     */
    public void send(String topic, Object data) {
        if (StringUtils.isEmpty(topic) || data == null) {
            throw new IllegalStateException("The send message parameter cannot be null");
        }
        try {
            ProducerRecord<String, String> kafkaMessage =  new ProducerRecord<>(topic, JSON.toJSONString(data));
            Future<RecordMetadata> metadataFuture = kafkaProducer.send(kafkaMessage);
            RecordMetadata recordMetadata = metadataFuture.get();
            log.info("MessageSenderClient kafka send Produce ok:{}",JSON.toJSONString(data));
        } catch (Exception e) {
            log.info("MessageSenderClient kafka send error,", e);
        }
    }

    /**
     * 消息发送至 kafka
     *
     * @param topic
     * @param partition 分区
     * @param data
     */
    public void send(String topic, Integer partition, Object data) {
        if (StringUtils.isEmpty(topic) || data == null) {
            throw new IllegalStateException("The send message parameter cannot be null");
        }
        if (partition == null) {
            send(topic, data);
        }
        try {
            ProducerRecord<String, String> kafkaMessage =  new ProducerRecord<>(topic, partition, null,JSON.toJSONString(data));
            Future<RecordMetadata> metadataFuture = kafkaProducer.send(kafkaMessage);
            RecordMetadata recordMetadata = metadataFuture.get();
            log.info("MessageSenderClient kafka send Produce ok:{}", recordMetadata.toString());
        } catch (Exception e) {
            log.info("MessageSenderClient kafka send error,", e);
        }
    }
}
