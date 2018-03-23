package com.comment.service.imp;

import com.comment.dao.NewsDao;
import com.comment.model.News;
import com.comment.service.inf.INewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImp implements INewsService {
    @Autowired
    private NewsDao newsDao;

    public List<News> findAll() {
        return newsDao.findAll();
    }
}
