package com.comment.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class User {
    private int id;
    private String username;
    private String pwd;
    private Date createtime;

}
