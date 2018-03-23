package com.comment.service.imp;

import com.comment.dao.CommentDao;
import com.comment.model.Comment;
import com.comment.service.inf.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImp implements ICommentService {
    @Autowired
    private CommentDao commentDao;

    public Integer Add(Comment comment) {
        return commentDao.Add(comment);
    }

    public List<Comment> findAllByTime() {
        return commentDao.findAllByTime();
    }

}
