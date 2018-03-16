package com.comment.common;

import com.alibaba.fastjson.JSON;
import com.comment.common.kafka.KafkaProperties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
public class KafkaUtil {

    private static final Logger logger = LoggerFactory.getLogger(KafkaUtil.class);

    private static KafkaProducer<String,String> kafkaProducer;
    private static KafkaConsumer<String,String> kafkaConsumer;
static {
    kafkaProducer =new KafkaProducer<>(KafkaProperties.Pget());
    kafkaConsumer =new KafkaConsumer<>(KafkaProperties.Cget());

}
    public  static void send(String topic,Object msg){
      String key =String.valueOf(System.currentTimeMillis());
        kafkaProducer.send(new ProducerRecord<>(topic,key,JSON.toJSONString(msg)));
    }
    public static void flush()
    {
        kafkaProducer.flush();
    }


    public  static void process(String topics){

        List<String> list = new ArrayList<>();
        list.add(topics);
        kafkaConsumer.subscribe(list);
            while (true) {
                ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
                for (ConsumerRecord<String, String> record : records)
                    System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
            }
        }
    }
