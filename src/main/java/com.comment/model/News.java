package com.comment.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class News {
    private int id;
    private String message;
    @JSONField(format="yyyy-MM-dd HH:mm:ss.SSS")
    private Date createtime;


}
