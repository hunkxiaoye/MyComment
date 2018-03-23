package com.comment.service.inf;

import com.comment.model.Comment;

import java.util.Date;
import java.util.List;

public interface ICommentService {
    Integer Add(Comment comment);
    List<Comment> findAllByTime();
}
