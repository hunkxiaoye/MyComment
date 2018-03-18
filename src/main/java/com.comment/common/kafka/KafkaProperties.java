package com.comment.common.kafka;


import org.apache.kafka.clients.consumer.KafkaConsumer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.stereotype.Component;


import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Component
public class KafkaProperties {

    private Map<String, String> producerPorpertis = null;

    private Map<String, String> consumerPorpertis = null;

    public KafkaProperties(){
        producerPorpertis = new HashMap<>();
        //String host = "hadoop2.jwl.com:9092,hadoop3.jwl.com:9092,hadoop4.jwl.com:9092";
        producerPorpertis.put("bootstrap.servers", "hadoop2.jwl.com:9092,hadoop3.jwl.com:9092,hadoop4.jwl.com:9092");
        producerPorpertis.put("acks", "all");
        producerPorpertis.put("retries", "0");
        producerPorpertis.put("batch.size", "16384");
        producerPorpertis.put("auto.commit.interval.ms", "1000");
        producerPorpertis.put("linger.ms", "0");
        producerPorpertis.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producerPorpertis.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producerPorpertis.put("block.on.buffer.full", "true");

        consumerPorpertis = new HashMap<>();
        //props.put("zookeeper.connect", "hadoop2.jwl.com:2181,hadoop3.jwl.com:2181,hadoop4.jwl.com:2181");
        consumerPorpertis.put("bootstrap.servers", "hadoop2.jwl.com:9092,hadoop3.jwl.com:9092,hadoop4.jwl.com:9092");
        //消费者的组id
        //consumerPorpertis.put("group.id", "test_yp");
        consumerPorpertis.put("enable.auto.commit", "true");
        consumerPorpertis.put("auto.commit.interval.ms", "1000");
        consumerPorpertis.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumerPorpertis.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    }

    public Map<String, String> getProducerPorpertis(){
        return producerPorpertis;
    }

    public Map<String, String> getConsumerPorpertis() {
        return consumerPorpertis;
    }
}
