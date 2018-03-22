package com.comment.service.imp;

import com.comment.dao.ReplyDao;
import com.comment.model.Reply;
import com.comment.service.inf.IReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ReplyServiceImp implements IReplyService {
    @Autowired
    private ReplyDao replyDao;

    public Integer Add(Reply reply) {
        return replyDao.Add(reply);
    }
    public Reply select(Integer id)
    {
        return replyDao.select(id);
    }

}
