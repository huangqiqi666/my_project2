package com.hikvision.myproject.kafka.listen;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

/**
 * @Classname KafkaConsumer
 * @Description kafka消费者（普通）
 * @Date 2022/8/15 16:20
 * @Created by huangqiqi
 */
@Component
public class KafkaConsumer {
    // 普通消费监听
    @KafkaListener(topics = {"topic1"})
    public void onMessage1(ConsumerRecord<?, ?> record){
        // 消费的哪个topic、partition的消息,打印出消息内容
        System.out.println("简单消费，接收到一条数据："+"topic:"+record.topic()+"|partition:"+record.partition()+"|offset:"+record.offset()+"|value:"+record.value());
    }

    /**
     * @Title 指定topic、partition、offset消费
     * @Description 同时监听topic1和topic2，监听topic1的0号分区、topic2的 "0号和1号" 分区，指向1号分区的offset初始值为8
     * 属性解释：
     * ① id：消费者ID；
     * ② groupId：消费组ID；
     * ③ topics：监听的topic，可监听多个；
     * ④ topicPartitions：可配置更加详细的监听信息，可指定topic、parition、offset监听。
     * 上面onMessage2监听的含义：监听topic1的0号分区，同时监听topic2的0号分区和topic2的1号分区里面offset从8开始的消息。
     * 注意：topics和topicPartitions不能同时使用；
     * @Author long.yuan
     * @Date 2020/3/22 13:38
     * @Param [record]
     * @return void
     **/
    @KafkaListener(id = "consumer1",groupId = "felix-group",topicPartitions = {
            @TopicPartition(topic = "topic1", partitions = { "0" }),
            @TopicPartition(topic = "topic2", partitions = "0", partitionOffsets = @PartitionOffset(partition = "1", initialOffset = "8"))
    })
    public void onMessage2(ConsumerRecord<?, ?> record) {
        System.out.println("接收到一条数据："+"topic:"+record.topic()+"|partition:"+record.partition()+"|offset:"+record.offset()+"|value:"+record.value());
    }
}