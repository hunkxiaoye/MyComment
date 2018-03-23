package com.comment.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class User {
    private int id;
    private String username;
    private String pwd;
    @JSONField(format="yyyy-MM-dd HH:mm:ss.SSS")
    private Date createtime;

}
