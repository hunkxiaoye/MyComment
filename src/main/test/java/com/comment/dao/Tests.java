package com.comment.dao;

import com.alibaba.fastjson.JSON;
import com.comment.common.cache.JedisUtil;
import com.comment.common.Solr.SolrUtil;
import com.comment.common.kafka.KafkaProducers;
import com.comment.common.kafka.TestConsumer;
import com.comment.job.CommentConsumer;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.io.IOException;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-mybatis.xml"})
public class Tests {
    @Autowired
    private ShardedJedisPool pool;

    @Autowired
    private JedisUtil redisClient;

    @Autowired
    private SolrUtil solrUtil;

//    @Test
//    public void Tests() {
//        List<Goods> list = goodsService.findGoodsAll();
//        String key = "TestGoods";
//        ShardedJedis jedis = pool.getResource();
//        String value = JSON.toJSONString(list);
//        jedis.set(key, value);
//        String result = jedis.get("TestGoods");
//        List<Goods> result_list = JSON.parseArray(result, Goods.class);
//        Goods model = result_list.get(1);
//        System.out.println(model);
//        //jedis.sort(key);//排序
//        System.out.println(jedis.exists("TestGoods"));//验证key是否存在
//        jedis.del("TestGoods");//删除key
//        System.out.println(jedis.exists("TestGoods"));//验证key是否存在
//
//        String key1 = "T1";
//        String val = "sadsada";
//        jedis.set(key1, val);
//        jedis.expire(key1, 10000);//设置key的生存时间
//        System.out.println(jedis.ttl(key1));
//        jedis.expire(key1, 9000);//更新生存时间
//        System.out.println(jedis.ttl(key1));
//        jedis.persist(key1);//删除过期时间
//        System.out.println(jedis.ttl(key1));//查看过期时间
//        System.out.println(jedis.type(key1));//返回值类型
//        //NX无则入 XX有则入 PX设置时间（毫秒） EX设置时间（秒）
//        String result_lock = jedis.set("lockKey", UUID.randomUUID().toString(), "NX", "PX", 90);
//        System.out.println(result_lock);
//        jedis.get("lockKey");
//        jedis.del("lockKey");
//
//        HashMap<String, Goods> hashMap = new HashMap<>();
//        hashMap.put("hash", model);
//        jedis.close();
//
//    }

    @Test
    public void JedisTest() {
        ShardedJedis jedis = pool.getResource();
        //String
        String key_string = "string";
        String val_string = "abcdefg";
        //jedis.set(key_string, val_string);
        redisClient.set(key_string, val_string);
        System.out.println(redisClient.get(key_string, String.class));
        //System.out.println(jedis.get(key_string));
        jedis.setnx(key_string, val_string);
        jedis.setex(key_string, 20, val_string);

        String nowval = jedis.getSet(key_string, val_string + "hijk");
        System.out.println(nowval);


        //list
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add("list" + i);
        }

        String key_list = "list";
        String val_list = JSON.toJSONString(list);
        jedis.lpush(key_list, val_list);
        jedis.rpop(key_list);


        //set
        HashSet<String> set = new HashSet<>();

        for (int i = 0; i < 5; i++) {
            set.add("set" + i);
        }
        String key_set = "set";
        String val_set = JSON.toJSONString(set);
        jedis.sadd(key_set, val_set);
        jedis.scard(key_set);


        //hash
        Map map = new HashMap();
        String key_hash = "hash";
        map.put("f1", "v1");
        map.put("f2", "v2");
        jedis.hset(key_hash, "f1", "v1");
        jedis.hget(key_hash, "f1");
        jedis.hmset("hash", map);
        List hashlist = jedis.hmget("key1", "f1", "f2");


        //common
        jedis.expire(key_string, 900);
        jedis.persist(key_string);
        jedis.exists(key_string);
        jedis.del(key_string);
        jedis.ttl(key_string);

    }

    final String solrUrl = "http://db1.jwl.com:8080/solr/SimpleOrder";

    @Test
    public void solrTestSelect() throws Exception {

        HttpSolrClient solrClient = new HttpSolrClient.Builder(solrUrl).build();
        SolrQuery solrQuery = new SolrQuery("*:*");//封装查询参数
        solrQuery.addField("orderId");
        solrQuery.addField("cinemaName");
        solrQuery.setRows(10);//每页显示条数
        QueryResponse response = solrClient.query(solrQuery);
        SolrDocumentList documents = response.getResults();

        for (SolrDocument doc : documents) {
            System.out.println("orderId:" + doc.get("orderId")
                    + "\tcinemaName:" + doc.get("cinemaName")
            );
        }
        solrClient.close();
    }

    @Test
    public void solrTestAdd() throws IOException, SolrServerException {

        HttpSolrClient solrClient = new HttpSolrClient.Builder(solrUrl).build();
        SolrInputDocument doc = new SolrInputDocument();

        doc.addField("orderId", 1076583647);
        doc.addField("cinemaName", "武穴万达");
        doc.addField("errorCode", 0);
        UpdateResponse updateResponse = solrClient.add(doc);
    }

    @Test
    public void solrTestUpdate() throws IOException, SolrServerException {

        HttpSolrClient solrClient = new HttpSolrClient.Builder(solrUrl).build();
        SolrInputDocument doc = new SolrInputDocument();

        doc.addField("orderId", 1076583647);
        Map<String, String> map = new HashMap<>();
        map.put("set", "杭州万达");
        doc.addField("cinemaName", map);
        //doc.addField("errorCode", 0);
        UpdateResponse updateResponse = solrClient.add(doc);
    }

    @Test
    public void solrTestDel() throws IOException, SolrServerException {
        HttpSolrClient solrClient = new HttpSolrClient.Builder(solrUrl).build();
        solrClient.deleteByQuery("orderId:10086");


    }

    @Test
    public void solrUtil() throws IOException, SolrServerException {
//        Order order = new Order();
//        order.setOrderId(10010L);
//        order.setCinemaName("黄石万达");
//        solrUtil.add(order,"SimpleOrder");
//       // solrUtil.del("orderId",10010,"SimpleOrder");
//        List<Object> list =new ArrayList<>();
//        list.add(1076583647);
//        list.add(21312313);
//        solrUtil.mdelbyid(list,"SimpleOrder");
        Map<String, String> map = new HashMap<>();
        map.put("createTime", "desc");
//        List<Order> orderlist = solrUtil.selectquery("", "SimpleOrder", map, 1, 3, null, null, Order.class);
//        for (Order order : orderlist)
//            System.out.println("id : " + order.getOrderid() + "cinemaName ：" + order.getCinemaname());
    }

    @Autowired
    private KafkaProducers kafkaProducers;

    @Test
    public void kafkaSendTest() {
        String topic = "yp_comment";
        String msg = "kafka test!";
        for (int i = 0; i < 100; i++)
            kafkaProducers.send(topic, msg);

        kafkaProducers.flush();
    }

    @Autowired
    private TestConsumer testConsumer;

    @Autowired
    private CommentConsumer commentConsumer;

    @Test
    public void kafkaprocessTest() throws InterruptedException {

        commentConsumer.start();

        Thread.currentThread().join();
    }

}
