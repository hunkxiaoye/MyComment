package com.comment.job;

import com.comment.common.kafka.AbstractConsumer;
import com.comment.common.kafka.annotation.KafkaConf;
import com.comment.model.Reply;
import com.comment.service.imp.SolrAndJedisUtilService;
import com.comment.service.inf.IReplyService;
import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@KafkaConf(topic = "yp_reply", groupid = "test_yp", threads = 1)
public class ReplyConsumer extends AbstractConsumer<Reply> {
    protected static final Logger log = LoggerFactory.getLogger(ReplyConsumer.class);
    protected static Map<String, String> sort = new HashMap<>();
    protected static String croename = "Reply";
    protected static Integer startIndex = 1;
    @Autowired
    private IReplyService replyService;
    @Autowired
    private SolrAndJedisUtilService solrAndJedisUtilService;

    protected boolean process(Reply msg) {

        sort.put("createtime", "desc");
        Integer count = 300;
        Integer pagesize = 10;
        String query = "comid:" + msg.getComid();
        String key = "comment" + msg.getComid();
        Long nums = 0L;
        try {
            adddb(msg);//写入数据库
            solrAndJedisUtilService.addsolr(msg, croename);//添加索引

            solrAndJedisUtilService.addredis(
                    query,
                    key,
                    sort,
                    croename,
                    startIndex,
                    pagesize,
                    count,
                    msg.getClass(),
                    nums);//更新缓存
        } catch (IOException e) {
            log.error("错误" + e);

        } catch (SolrServerException e) {
            log.error("错误" + e);

        }

        return false;
    }

    private Integer adddb(Reply reply) {
        return replyService.Add(reply);
    }

}
