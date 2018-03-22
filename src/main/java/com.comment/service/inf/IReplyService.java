package com.comment.service.inf;

import com.comment.model.Reply;

public interface IReplyService {
    Integer Add(Reply reply);
    Reply select(Integer id);
}
