package com.comment.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.comment.model.Goods;
import com.comment.service.inf.IGoodsService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrResponse;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import javax.lang.model.element.NestingKind;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-mybatis.xml"})
public class Tests {
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private ShardedJedisPool pool;

    @Test
    public void Tests() {
        List<Goods> list = goodsService.findGoodsAll();
        String key = "TestGoods";
        ShardedJedis jedis = pool.getResource();
        String value = JSON.toJSONString(list);
        jedis.set(key, value);
        String result = jedis.get("TestGoods");
        List<Goods> result_list = JSON.parseArray(result, Goods.class);
        Goods model = result_list.get(1);
        System.out.println(model);
        //jedis.sort(key);//排序
        System.out.println(jedis.exists("TestGoods"));//验证key是否存在
        jedis.del("TestGoods");//删除key
        System.out.println(jedis.exists("TestGoods"));//验证key是否存在

        String key1 = "T1";
        String val = "sadsada";
        jedis.set(key1, val);
        jedis.expire(key1, 10000);//设置key的生存时间
        System.out.println(jedis.ttl(key1));
        jedis.expire(key1, 9000);//更新生存时间
        System.out.println(jedis.ttl(key1));
        jedis.persist(key1);//删除过期时间
        System.out.println(jedis.ttl(key1));//查看过期时间
        System.out.println(jedis.type(key1));//返回值类型
        //NX无则入 XX有则入 PX设置时间（毫秒） EX设置时间（秒）
        String result_lock = jedis.set("lockKey", UUID.randomUUID().toString(), "NX", "PX", 90);
        System.out.println(result_lock);
        jedis.get("lockKey");
        jedis.del("lockKey");

        HashMap<String, Goods> hashMap = new HashMap<>();
        hashMap.put("hash", model);


    }

    @Test
    public void JedisTest() {
        ShardedJedis jedis = pool.getResource();

        //String
        String key_string = "string";
        String val_string = "abcdefg";
        jedis.set(key_string, val_string);

        System.out.println(jedis.get(key_string));
        jedis.setnx(key_string, val_string);
        jedis.setex(key_string, 20, val_string);

        String nowval = jedis.getSet(key_string, val_string+"hijk");
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
        List hashlist = jedis.hmget("key1","f1","f2");



        //common
        jedis.expire(key_string, 900);
        jedis.persist(key_string);
        jedis.exists(key_string);
        jedis.del(key_string);
        jedis.ttl(key_string);

    }

    public void solrTest(){

        final String solrUrl = "http://localhost:8080/solr";

        HttpSolrClient solrClient = new HttpSolrClient.Builder(solrUrl).build();
        SolrQuery solrQuery = new SolrQuery("*:*");//封装查询参数
        solrQuery.addField("id");
        solrQuery.addField("name");
        solrQuery.setRows(10);//每页显示条数
        //SolrResponse response =

    }
}
