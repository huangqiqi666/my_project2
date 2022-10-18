package com.example.kafka3;
import com.example.kafka3.config.MessageSenderClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class KafkaTest {

    @Autowired
    private MessageSenderClient kafkaProducer;

    /**
     * 发消息
     */
    @Test
   void testKafkaProducer() {
        kafkaProducer.send("test", "payment发送一条新消息");
    }
}

