package com.comment.job;

import com.comment.common.kafka.AbstractConsumer;
import com.comment.common.kafka.annotation.KafkaConf;
import com.comment.model.Comment;
import com.comment.service.imp.SolrAndJedisUtilService;
import com.comment.service.inf.ICommentService;
import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@KafkaConf(topic = "yp_comment", groupid = "test_yp", threads = 1)
public class CommentConsumer extends AbstractConsumer<Comment> {

    protected static final Logger log = LoggerFactory.getLogger(CommentConsumer.class);
    protected static Map<String, String> sort = new HashMap<>();
    protected static String croename ="Comment";
    protected static Integer startIndex=1;
    @Autowired
    private ICommentService commentService;
    @Autowired
    private SolrAndJedisUtilService solrAndJedisUtilService;


    protected void process(Comment msg) {

        sort.put("createTime", "desc");
        Integer count =100;
        Integer pagesize =20;
        String query ="id:"+msg.getNewsid();
        String key ="news"+msg.getNewsid();

        try {
            adddb(msg);//写入数据库
            solrAndJedisUtilService.addsolr(msg,croename);//添加索引
            solrAndJedisUtilService.addredis(
                    query,
                    key,
                    sort,
                    croename,
                    startIndex,
                    pagesize,
                    count,
                    msg.getClass());//更新缓存
        } catch (IOException e) {
            log.error("错误" + e);

        } catch (SolrServerException e) {
            log.error("错误" + e);

        }

    }

    private void adddb(Comment comment) {
        commentService.Add(comment);
    }

}
