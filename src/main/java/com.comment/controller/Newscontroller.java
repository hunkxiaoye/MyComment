package com.comment.controller;

import com.comment.model.News;
import com.comment.service.imp.NewsServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class Newscontroller {
    @Autowired
    private NewsServiceImp newsService;

    @RequestMapping(value = "/newspage")
    public String newslist(Model model) {
        List<News> list =newsService.findAll();
        model.addAttribute("list", list);
        return "newspage";
    }
}
