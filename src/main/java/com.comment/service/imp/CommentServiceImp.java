package com.comment.service.imp;

import com.comment.dao.CommentDao;
import com.comment.model.Comment;
import com.comment.service.inf.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImp implements ICommentService {
    @Autowired
    private CommentDao commentDao;
   public void Add(Comment comment)
    {
        commentDao.Add(comment);
    }
}