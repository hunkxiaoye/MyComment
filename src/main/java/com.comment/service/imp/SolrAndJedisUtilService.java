package com.comment.service.imp;

import com.alibaba.fastjson.JSON;
import com.comment.common.Solr.SolrUtil;
import com.comment.common.ToolsUtils;
import com.comment.common.cache.JedisUtil;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SolrAndJedisUtilService {


    @Autowired
    private SolrUtil solrUtil;
    @Autowired
    private JedisUtil jedis;

    public <T> void addsolr(T t, String corname) throws IOException, SolrServerException {
        solrUtil.addAndupdate(t, corname);
    }


    /**
     * @param query      查询语句
     * @param key        缓存的大key
     * @param sort       solr 排序规则
     * @param croename   核心名称
     * @param startIndex 开始页数
     * @param pageSize   每页记录数
     * @param count      要获取的最大条数
     * @param clazz      获取数据类型
     * @param <T>        返回的数据类型
     * @throws IOException
     * @throws SolrServerException
     */
    public <T> Boolean addredis(String query, String key, Map<String, String> sort, String croename,
                             Integer startIndex, Integer pageSize, Integer count, Class<T> clazz,Long nums)
            throws IOException, SolrServerException {

        Map<String, String> result = new HashMap<>();
        List<T> list = solrUtil.selectquery(query,
                croename, sort, startIndex,null,count,
                null, null, clazz,nums);

        List<List<T>> lists = ToolsUtils.splitList(list, pageSize);

        for (int i = 0; i < lists.size(); i++) {
            result.put(String.valueOf(i + 1), JSON.toJSONString(lists.get(i)));
        }
        if (result.size()!=0) {
            jedis.hmset(String.valueOf(key), result);
            return true;

        }

        return false;

    }

    public <T> List<T> magicredis(String query, String key, Map<String, String> sort,String croename,
                                  Integer startIndex,Integer pageSize, Integer count, Class<T> clazz)
            throws IOException, SolrServerException {
        Long nums=0L;

        List<T> list = solrUtil.selectquery(query,
                croename, sort, startIndex,pageSize,null,null,
                null,clazz,nums);
        return list;


    }

}
