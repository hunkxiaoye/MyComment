package com.comment.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.solr.client.solrj.beans.Field;

import java.util.Date;
@Setter
@Getter
public class Comment {
    @Field
    private int id;
    @Field
    private int userid;
    @Field
    private String commentinfo;
    @Field
    private Date createtime;
    @Field
    private int newsid;

}
