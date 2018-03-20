package com.comment.job;

import com.comment.common.Solr.SolrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class commentjob {
    @Autowired
    private CommentConsumer commentConsumer;
    @Autowired
    private SolrUtil solrUtil;

    @Scheduled(fixedDelay = 100)
    public void execute() {

        //执行从队列获取评论插入数据库并创建索引
        try {
            System.out.println("任务开始");
            commentConsumer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
