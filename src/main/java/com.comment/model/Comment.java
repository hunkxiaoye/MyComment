package com.comment.model;

import org.apache.solr.client.solrj.beans.Field;

import java.util.Date;

public class Comment {
    @Field("Id")
    private int id;
    @Field("UserId")
    private int userid;
    @Field("CommentInfo")
    private String commentinfo;
    @Field("CreateTime")
    private Date createtime;
    @Field("NewsId")
    private int newsid;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
