package com.hikvision.myproject.kafka2;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootTest
class Kafka2ApplicationTests {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Test
    public void testDemo() throws InterruptedException {
        kafkaTemplate.send("topic.quick.demo", "this is my first demo");
        //休眠5秒，为了使监听器有足够的时间监听到topic的数据
        Thread.sleep(5000);
    }
}
