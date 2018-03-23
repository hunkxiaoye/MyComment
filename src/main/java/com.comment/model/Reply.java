package com.comment.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import org.apache.solr.client.solrj.beans.Field;

import java.util.Date;

@Getter
@Setter
public class Reply {
    @Field
    private int id;
    @Field
    private int comid;
    @Field
    private int userid;
    @Field
    private String replyinfo;
    @Field
    @JSONField(format="yyyy-MM-dd HH:mm:ss.SSS")
    private Date createtime;


}
