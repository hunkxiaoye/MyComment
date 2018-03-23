package com.comment.service.inf;

import com.comment.model.Reply;

import java.util.Date;
import java.util.List;

public interface IReplyService {
    Integer Add(Reply reply);
    Reply select(Integer id);
    List<Reply> findAllByTime();
}
