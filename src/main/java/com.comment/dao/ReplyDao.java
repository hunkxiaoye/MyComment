package com.comment.dao;

import com.comment.model.Reply;

public interface ReplyDao {
    Integer Add(Reply reply);
    Reply select(Integer id);
}
