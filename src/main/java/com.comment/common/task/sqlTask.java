package com.comment.common.task;

import com.comment.common.Solr.SolrUtil;
import com.comment.model.Comment;
import com.comment.model.Reply;
import com.comment.service.inf.ICommentService;
import com.comment.service.inf.IReplyService;
import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 数据库同步到索引定时任务
 */
@Component
public class sqlTask {
    private static final Logger log = LoggerFactory.getLogger(sqlTask.class);
    @Autowired
    private ICommentService commentService;
    @Autowired
    private IReplyService replyService;
    @Autowired
    private SolrUtil solrUtil;

    private String commentCorname = "Comment";
    private String replyCorname = "Reply";


    @Scheduled(fixedDelay = 60000 * 5)
    public void commentwork() {
        System.out.println("Comment任务开始");
        List<Comment> commentList = commentService.findAllByTime();
        if (commentList.size() == 0) {
            System.out.println("没有要同步的数据------任务结束");
            return;
        }

        solrUtil.addAndupdate(commentList, commentCorname);

        System.out.println("任务结束");
    }

    @Scheduled(fixedDelay = 60000 * 5)
    public void replywork() {
        System.out.println("Reply任务开始");
        List<Reply> replyList = replyService.findAllByTime();
        if (replyList.size() == 0) {
            System.out.println("没有要同步的数据------任务结束");
            return;
        }
        solrUtil.addAndupdate(replyList, replyCorname);

        System.out.println("任务结束");
    }
}
