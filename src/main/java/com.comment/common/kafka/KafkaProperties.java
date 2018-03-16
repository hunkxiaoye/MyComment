package com.comment.common.kafka;


import org.apache.kafka.clients.consumer.KafkaConsumer;

import org.springframework.stereotype.Component;


import java.util.Properties;

@Component
public class KafkaProperties {


    public static Properties Pget() {
        //"hadoop2.jwl.com:9092,hadoop3.jwl.com:9092,hadoop4.jwl.com:9092"
        Properties props = new Properties();
        //String host = "hadoop2.jwl.com:9092,hadoop3.jwl.com:9092,hadoop4.jwl.com:9092";
        props.put("bootstrap.servers", "hadoop2.jwl.com:9092,hadoop3.jwl.com:9092,hadoop4.jwl.com:9092");
        props.put("acks", "all");
        props.put("retries", "0");
        props.put("batch.size", "16384");
        props.put("auto.commit.interval.ms", "1000");
        props.put("linger.ms", "0");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("block.on.buffer.full", "true");
        return props;

    }

    public static Properties Cget() {

        Properties props = new Properties();
        //props.put("zookeeper.connect", "hadoop2.jwl.com:2181,hadoop3.jwl.com:2181,hadoop4.jwl.com:2181");
        props.put("bootstrap.servers", "hadoop2.jwl.com:9092,hadoop3.jwl.com:9092,hadoop4.jwl.com:9092");
        //消费者的组id
        props.put("group.id", "test_yp");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return props;

    }
}
