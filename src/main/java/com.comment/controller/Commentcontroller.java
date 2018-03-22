package com.comment.controller;

import com.alibaba.fastjson.JSON;
import com.comment.common.CookieUtils;
import com.comment.common.StringUtils;
import com.comment.common.ToolsUtils;
import com.comment.common.cache.JedisUtil;
import com.comment.common.kafka.KafkaProducers;
import com.comment.model.Comment;
import com.comment.model.Reply;
import com.comment.service.inf.ICommentService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Controller
public class Commentcontroller {
    @Autowired
    private JedisUtil jedis;
    @Autowired
    private KafkaProducers producers;
    @Autowired
    private ICommentService commentService;


    /**
     * 评论列表展示
     * @param model
     * @param newsid
     * @param pagenumber
     * @return
     */
    @RequestMapping(value = "/getcomment")
    public String getcomment(Model model, Integer newsid,@RequestParam(defaultValue = "1") Integer pagenumber){
        //获取指定页缓存列表
        List<Comment> list =JSON.parseArray(jedis.hmget("news"+newsid,
                String.valueOf(pagenumber)).get(0),Comment.class);
        if (list==null||list.size()==0) {
            if (pagenumber!=1){
                model.addAttribute("lastpage",pagenumber-1);
                model.addAttribute("nextpage",-1);
            }else {
                model.addAttribute("lastpage", 0);
                model.addAttribute("nextpage", 0);
            }
        }else {
            model.addAttribute("list",list);
            if (pagenumber==1) {
                model.addAttribute("lastpage",0);
                model.addAttribute("nextpage",pagenumber+1);
            }else {
                model.addAttribute("lastpage",pagenumber-1);
                model.addAttribute("nextpage",pagenumber+1);
            }


        }
        model.addAttribute("newsid",newsid);

        return "commentlist";

    }



    /**
     * 展示添加评论页
     * @param newsid
     * @param model
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/addcomment")
    public String addcomment(Integer newsid ,Model model,HttpServletRequest request) throws Exception {
                  Comment comment =new Comment();
                  comment.setNewsid(newsid);
                  comment.setUserid(Integer.parseInt(CookieUtils.getLoginInfo(request)[1]));
                  model.addAttribute("model",comment);
                  return "createcomment";
    }

    /**
     * 新增评论插入消息队列
     * @param comment
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/sendcomment")
    public String sendcomment(Comment comment) throws Exception {
        comment.setCreatetime(new Date());
        producers.send("yp_comment",comment);
        return "redirect:/getcomment?newsid="+comment.getNewsid();
    }
}
