package com.comment.job;

import com.comment.common.Solr.SolrUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

@Component
public class commentjob implements InitializingBean, ServletContextAware {
    @Autowired
    private CommentConsumer commentConsumer;
    @Autowired
    private ReplyConsumer replyConsumer;


    public void Start() {

        //执行从队列获取评论插入数据库并创建索引
        try {
            System.out.println("评论相关任务开始");
            commentConsumer.start();
            System.out.println("回复相关任务开始");
            replyConsumer.start();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void setServletContext(ServletContext servletContext) {
        try {
            Start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void afterPropertiesSet() throws Exception {
    }

}
