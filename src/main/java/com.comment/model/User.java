package com.comment.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class User {
    private int id;
    private String username;
    private String pwd;
    private Date createtime;

}
