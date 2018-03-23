package com.comment.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import org.apache.solr.client.solrj.beans.Field;

import java.util.Date;
@Getter
@Setter
public class Comment {
    @Field
    private int id;
    @Field
    private int userid;
    @Field
    private String commentinfo;
    @Field
    @JSONField(format="yyyy-MM-dd HH:mm:ss.SSS")
    private Date createtime;
    @Field
    private int newsid;

}
