package com.comment.controller;

import com.alibaba.fastjson.JSON;
import com.comment.common.CookieUtils;
import com.comment.common.cache.JedisUtil;
import com.comment.common.kafka.KafkaProducers;
import com.comment.dao.ReplyDao;
import com.comment.model.Comment;
import com.comment.model.Reply;
import com.comment.service.inf.IReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
@Controller
public class Replycontroller {
    @Autowired
    private JedisUtil jedis;
    @Autowired
    private KafkaProducers producers;

    /**
     * send消息队列
     * @param reply
     * @param cominfo
     * @return
     */
    @RequestMapping(value = "/sendreply")
    public String addreply(Reply reply, String cominfo) throws UnsupportedEncodingException {
        reply.setCreatetime(new Date());
        producers.send("yp_reply", reply);
        return "redirect:/getcommentdetail?commentid=" + reply.getComid() +
                "&text=" +java.net.URLEncoder.encode(cominfo,"UTF-8");
    }

    /**
     * 添加回复页
     * @param comid
     * @param text
     * @param model
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/addreplypage")
    public String addreplypage(Integer comid, String text, Model model, HttpServletRequest request) throws Exception {
        Reply reply = new Reply();
        reply.setComid(comid);
        reply.setUserid(Integer.parseInt(CookieUtils.getLoginInfo(request)[1]));
        model.addAttribute("model", reply);
        model.addAttribute("text", text);
        return "createreply";
    }

    /**
     * 回复列表
     * @param model
     * @param commentid
     * @param text
     * @param pagenumber
     * @return
     */
    @RequestMapping(value = "/getcommentdetail")
    public String getcommentdetail(Model model, Integer commentid, String text, @RequestParam(defaultValue = "1") Integer pagenumber) {
        //获取指定页缓存列表
        List<Reply> list = JSON.parseArray(jedis.hmget("comment" + commentid,
                String.valueOf(pagenumber)).get(0), Reply.class);
        if (list == null || list.size() == 0) {
            if (pagenumber != 1) {
                model.addAttribute("lastpage", pagenumber - 1);
                model.addAttribute("nextpage", -1);
            } else {
                model.addAttribute("lastpage", 0);
                model.addAttribute("nextpage", 0);
            }
        } else {
            model.addAttribute("list", list);
            if (pagenumber == 0) {
                model.addAttribute("lastpage", 0);
                model.addAttribute("nextpage", pagenumber + 1);
            } else {
                model.addAttribute("lastpage", pagenumber - 1);
                model.addAttribute("nextpage", pagenumber + 1);
            }


        }
        model.addAttribute("comid", commentid);
        model.addAttribute("commentInfo", text);
        return "commentdetail";
    }

}
