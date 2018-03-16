package com.comment.common.kafka;

import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class KafkaProperties {

    private KafkaProducer<String, String> producer;
    private KafkaConsumer<String, String> consumer;


    public KafkaProducer Pget() {
        //"hadoop2.jwl.com:9092,hadoop3.jwl.com:9092,hadoop4.jwl.com:9092"
        Properties props = new Properties();
        String host = "hadoop2.jwl.com:9092,hadoop3.jwl.com:9092,hadoop4.jwl.com:9092";
        props.put("bootstrap.servers", host);
        props.put("request.required.acks", "-1");
        props.put("producer.type", "sync");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return producer = new KafkaProducer<>(props);


    }

    public KafkaConsumer Cget() {
        Properties props = new Properties();

        props.put("zookeeper.connect", "hadoop2.jwl.com:2181");

        //消费者的组id
        props.put("group.id", "yp");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("zookeeper.session.timeout.ms", "10000");
        props.put("zookeeper.sync.time.ms", "200");
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        return consumer = new KafkaConsumer<>(props);
    }
}
