package com.comment.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.comment.common.Elasticsearch.meta.BaseModel;
import org.apache.solr.client.solrj.beans.Field;

import java.time.LocalDateTime;
import java.util.Date;

public class Comment extends BaseModel<Integer> {
    @Field
    private int id;
    @Field
    private int userid;
    @Field
    private String commentinfo;
//    @JSONField(format="yyyy-MM-dd HH:mm:ss.SSS")
    @Field
    private Date createtime;
    @Field
    private int newsid;


    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getCommentinfo() {
        return commentinfo;
    }

    public void setCommentinfo(String commentinfo) {
        this.commentinfo = commentinfo;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public int getNewsid() {
        return newsid;
    }

    public void setNewsid(int newsid) {
        this.newsid = newsid;
    }
}
