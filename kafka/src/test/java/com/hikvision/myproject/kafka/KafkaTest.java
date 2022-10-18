package com.hikvision.myproject.kafka;


import com.hikvision.myproject.kafka.controller.KafkaProducer;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Classname KafkaTest
 * @Description TODO
 * @Date 2022/8/17 9:30
 * @Created by huangqiqi
 */
@SpringBootTest(classes = KafkaApplication.class)
@RunWith(SpringRunner.class)
public class KafkaTest {

    @Autowired
    KafkaProducer kafkaProducer;

    @Test
    public void context(){
        kafkaProducer.sendMessage1("这是一条测试数据");
    }
}
