package com.comment.dao;


import com.comment.model.Reply;

import java.util.Date;
import java.util.List;

public interface ReplyDao {
    Integer Add(Reply reply);
    Reply select(Integer id);
    List<Reply> findAllByTime();
}
