package com.comment.common.kafka;


import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Component
public class KafkaProperties {
    private static volatile Map<String, Object> Clients = new HashMap<>();
    private static final Object _lock = new Object();

    public KafkaProducer Pget() {

        String name = "producer";
        if (!Clients.containsKey(name)) {
            synchronized (_lock) {
                if (!Clients.containsKey(name)) {
                    //"hadoop2.jwl.com:9092,hadoop3.jwl.com:9092,hadoop4.jwl.com:9092"
                    Properties props = new Properties();
                    String host = "hadoop2.jwl.com:9092,hadoop3.jwl.com:9092,hadoop4.jwl.com:9092";
                    props.put("bootstrap.servers", host);
                    props.put("request.required.acks", "-1");
                    props.put("producer.type", "sync");
                    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
                    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
                    KafkaProducer<String, String> producer = new KafkaProducer<>(props);
                    Clients.put(name, producer);
                    return producer;
                }


            }
        }
        return (KafkaProducer<String, String>) Clients.get(name);
    }

    public KafkaConsumer Cget() {
        String name = "consumer";
        if (!Clients.containsKey(name)) {
            synchronized (_lock) {
                if (!Clients.containsKey(name)) {
                    Properties props = new Properties();
                    props.put("zookeeper.connect", "hadoop2.jwl.com:2181");
                    //消费者的组id
                    props.put("group.id", "yp");
                    props.put("enable.auto.commit", "true");
                    props.put("auto.commit.interval.ms", "1000");
                    props.put("zookeeper.session.timeout.ms", "10000");
                    props.put("zookeeper.sync.time.ms", "200");
                    props.put("serializer.class", "kafka.serializer.StringEncoder");
                    KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
                    Clients.put(name, consumer);
                    return consumer;
                }
            }
        }
        return (KafkaConsumer<String, String>) Clients.get(name);
    }
}
