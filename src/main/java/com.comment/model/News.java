package com.comment.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class News {
    private int id;
    private String message;
    private Date createtime;


}
