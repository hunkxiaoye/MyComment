package com.comment.model;

import java.util.Date;

public class Reply {
    private int id;
    private int comid;
    private int userid;
    private String replyinfo;
    private Date createtime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getComid() {
        return comid;
    }

    public void setComid(int comid) {
        this.comid = comid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getReplyinfo() {
        return replyinfo;
    }

    public void setReplyinfo(String replyinfo) {
        this.replyinfo = replyinfo;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
