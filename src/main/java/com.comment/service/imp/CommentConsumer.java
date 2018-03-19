package com.comment.service.imp;

import com.alibaba.fastjson.JSON;
import com.comment.common.Solr.SolrUtil;
import com.comment.common.ToolsUtils;
import com.comment.common.cache.JedisUtil;
import com.comment.common.kafka.AbstractConsumer;
import com.comment.common.kafka.annotation.KafkaConf;
import com.comment.model.Comment;
import com.comment.service.inf.ICommentService;
import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@KafkaConf(topic = "yp_comment", groupid = "test_yp", threads = 4)
public class CommentConsumer extends AbstractConsumer<Comment> {

    protected static final Logger log = LoggerFactory.getLogger(CommentConsumer.class);
    @Autowired
    private ICommentService commentService;
    @Autowired
    private SolrUtil solrUtil;
    private JedisUtil jedis;

    protected void process(Comment msg) {


        try {
            adddb(msg);//写入数据库
            addsolr(msg);//添加索引
            addredis(msg);//更新缓存
        } catch (IOException e) {
            log.error("错误" + e);

        } catch (SolrServerException e) {
            log.error("错误" + e);

        }

    }

    private void adddb(Comment comment) {
        commentService.Add(comment);
    }

    private void addsolr(Comment comment) throws IOException, SolrServerException {
        solrUtil.add(comment, "SimpleOrder");
    }


    /**
     * 获取索引 更新缓存
     *
     * @param comment
     * @throws IOException
     * @throws SolrServerException
     */
    private void addredis(Comment comment) throws IOException, SolrServerException {

        Map<String, String> map = new HashMap<>();
        Map<String, String> result = new HashMap<>();
        map.put("createTime", "desc");
        List<Comment> list = solrUtil.selectquery("id:" + comment.getNewsid(),
                "SimpleOrder", map, 1, 10,
                null, null, Comment.class);
        List<List<Comment>> lists = ToolsUtils.splitList(list, 20);
        //如果有缓存则删除重新创建
        if (jedis.hgetAll(String.valueOf(comment.getNewsid())) != null) {
             jedis.del(String.valueOf(comment.getNewsid()));
        }
        for (int i = 0; i < 5; i++) {
            result.put(String.valueOf(i + 1), JSON.toJSONString(lists.get(i)));
        }
        jedis.hmset(String.valueOf(comment.getNewsid()), result);

    }
}
