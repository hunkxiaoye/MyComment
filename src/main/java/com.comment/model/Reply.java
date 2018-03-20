package com.comment.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.solr.client.solrj.beans.Field;

import java.util.Date;

@Setter
@Getter
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
    private Date createtime;


}
