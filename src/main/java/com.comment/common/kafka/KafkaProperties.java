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
        /* 自动确认offset的时间间隔  */
        consumerPorpertis.put("auto.commit.interval.ms", "1000");
        consumerPorpertis.put("session.timeout.ms", "20000");
        //消息发送的最长等待时间.需大于session.timeout.ms这个时间
        consumerPorpertis.put("request.timeout.ms", "31000");
        //一次从kafka中poll出来的数据条数
        //max.poll.records条数据需要在在session.timeout.ms这个时间内处理完
        consumerPorpertis.put("max.poll.records","1");
        //server发送到消费端的最小数据，若是不满足这个数值则会等待直到满足指定大小。默认为1表示立即接收。
        consumerPorpertis.put("fetch.min.bytes", "1");
        //若是不满足fetch.min.bytes时，等待消费端请求的最长等待时间
        consumerPorpertis.put("fetch.wait.max.ms", "1000");
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

