package com.comment.common.kafka;

import com.comment.common.kafka.annotation.KafkaConf;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@KafkaConf(topic = "yp_comment", groupid = "test_yp")
public class TestConsumer extends AbstractConsumer<String> {

    public void  process(String msg) {
        System.out.println("msg:" + msg);
    }

}
