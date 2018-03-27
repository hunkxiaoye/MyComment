package com.comment.controller;

import com.alibaba.fastjson.JSON;
import com.comment.common.ToolsUtils;
import com.comment.common.cache.JedisUtil;
import com.comment.model.Comment;
import com.comment.model.News;
import com.comment.model.Reply;
import com.comment.service.imp.NewsServiceImp;
import com.comment.service.imp.SolrAndJedisUtilService;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class Newscontroller {
    @Autowired
    private NewsServiceImp newsService;
    @Autowired
    private JedisUtil jedis;
    @Autowired
    private SolrAndJedisUtilService solrAndJedisUtilService;

    @RequestMapping(value = "/index")
    public String newslist(Model model) {
        List<News> list = newsService.findAll();
        model.addAttribute("list", list);
        return "newspage";
    }

    /**
     * 从搜索引擎获取数据重新创建缓存
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
    @RequestMapping(value = "/magic")
    public String magic() throws IOException, SolrServerException {
        Map<String, String> sort = new HashMap<>();
        int count = 200;
        int pagesize = 10;
        int sum = 1;
        int comid;
        boolean islist = true;
        String commentCroename = "Comment";
        String replyCroename = "Reply";
        sort.put("createtime", "desc");
        String querys;
        String query;
        String keys;
        String key;
        Long nums = 0L;

        List<News> list = newsService.findAll();
        for (News model : list) {

            querys = "newsid:" + model.getId();
            keys = "news" + model.getId();
            while (islist) {
                List<Comment> commentslist = solrAndJedisUtilService.magicredis(
                        querys,
                        keys,
                        sort,
                        commentCroename,
                        sum,
                        pagesize,
                        count,
                        Comment.class);
                if (commentslist.size() < 10){
                    islist = false; //表示遍历到最后一页了
                    if (commentslist.size()==0)
                        break;
                }
                //获取前20页评论创建缓存
                if (sum < 21) {
                   // List<List<Comment>> lists = ToolsUtils.splitList(commentslist, pagesize);
                    Map<String, String> result = new HashMap<>();
                    result.put(String.valueOf(sum), JSON.toJSONString(commentslist));
                    jedis.hmset(String.valueOf(keys), result);
                }
                sum += 1;
                for (int i = 0; i < commentslist.size(); i++) {
                    comid = commentslist.get(i).getId();
                    query = "comid:" + comid;
                    key = "comment" + comid;
                    if (!solrAndJedisUtilService.addredis(
                            query,
                            key,
                            sort,
                            replyCroename,
                            1,
                            pagesize,
                            count,
                            Reply.class,
                            nums))//更新缓存{
                        continue;
                }
            }
        }
        return "redirect:/index";
    }
}
