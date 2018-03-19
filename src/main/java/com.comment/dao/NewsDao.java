package com.comment.dao;

import com.comment.model.News;

import java.util.List;

public interface NewsDao {

    List<News> findAll();
}
