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
    public  static void send(String topic,String msg){
        kafkaProducer.send(new ProducerRecord<>(topic,msg));
    }


    public  static void process(){
        while (true){
            ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
            for (ConsumerRecord<String,String> record : records){
                String msg = record.value();
                String key = record.key();
                System.out.println(String.format("offset:%d ,key:%d ,value:%s%n",record.offset(), key, msg));
                try {
                    logger.info("消息处理成功, ID: {}, 内容: {}", key, msg);
                }catch (Exception e){

                        logger.error("消息处理失败, ID: {}, 内容: {}, 错误:{}", key, msg, e);
                    }
                }
            }
        }
    }
