package com.comment.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.comment.model.Goods;
import com.comment.service.inf.IGoodsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-mybatis.xml"})
public class Tests {
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private ShardedJedisPool pool;
    @Test
    public void Tests(){
        List<Goods> list = goodsService.findGoodsAll();
        String key ="TestGoods";
        ShardedJedis jedis = pool.getResource();
        String value = JSON.toJSONString(list);
        jedis.set(key,value);
        String result = jedis.get("TestGoods");
        List<Goods> result_list = JSON.parseArray(result, Goods.class);
        Goods model = result_list.get(1);
        System.out.println(model);

    }
}
