package com.comment.dao;

import com.comment.model.Comment;

import java.util.Date;
import java.util.List;

public interface CommentDao {
    Integer Add(Comment comment);
   List<Comment> findAllByTime();
}
