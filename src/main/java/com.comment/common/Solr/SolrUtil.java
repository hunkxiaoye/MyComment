package com.comment.common.Solr;


import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;


@Component
public class SolrUtil {

    private static final Logger log = LoggerFactory.getLogger(SolrUtil.class);

    @Autowired
    private SolrClientUtil solrClientUtil;

    public void addAndupdate(Object model, String corname) throws IOException, SolrServerException {
        HttpSolrClient client = solrClientUtil.getWriteServer(corname);
        client.addBean(model);
        client.commit();
    }

    /**
     * 删除（不推荐）
     *
     * @param idname  删除id 名称
     * @param idvalue
     * @param corname
     * @throws IOException
     * @throws SolrServerException
     */
    public void querydel(String idname, Object idvalue, String corname)
            throws IOException, SolrServerException {
        String deltxt = idname + ":" + String.valueOf(idvalue);
        solrClientUtil.getWriteServer(corname).deleteByQuery(deltxt);
    }

    /**
     * 批量删除 （不推荐）
     *
     * @param idname
     * @param values
     * @param corname
     * @throws IOException
     * @throws SolrServerException
     */
    public void mquerydel(String idname, List<Object> values, String corname)
            throws IOException, SolrServerException {
        String deltxt = "";
        for (int i = 0; i < values.size(); i++) {
            if (i == (values.size() - 1))
                deltxt += idname + ":" + String.valueOf(values.get(i));
            else {
                deltxt += idname + ":" + String.valueOf(values.get(i) + " OR ");
            }

        }

        solrClientUtil.getWriteServer(corname).deleteByQuery(deltxt);

    }

    /**
     * 删除单体
     *
     * @param id      索引id
     * @param corname 核心名称
     * @throws IOException
     * @throws SolrServerException
     */
    public void delbyid(Object id, String corname) throws IOException, SolrServerException {
        solrClientUtil.getWriteServer(corname).deleteById(String.valueOf(id));
    }

    /**
     * 批量删除
     *
     * @param ids     索引id
     * @param corname 核心名称
     * @throws IOException
     * @throws SolrServerException
     */
    public void mdelbyid(List<Object> ids, String corname) throws IOException, SolrServerException {
        List<String> list = new ArrayList<>();
        for (Object obj : ids) {
            list.add(String.valueOf(obj));
        }

        solrClientUtil.getWriteServer(corname).deleteById(list);
    }


    /**
     * @param query       查询语句
     * @param corename    核心名称
     * @param sort        排序
     * @param startIndex  开始页数
     * @param pageSize    每页的条数
     * @param facetFileds
     * @param filterQuery
     * @param clazz       实体类型
     * @param <T>
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
    public <T> List<T> selectquery(String query, String corename, Map<String, String> sort,
                                   Integer startIndex, Integer pageSize, String[] facetFileds,
                                   String filterQuery, Class<T> clazz, Long nums) throws IOException, SolrServerException {
        SolrQuery solrQuery = new SolrQuery();//封装查询参数
        solrQuery.setQuery("*:*");

        if (filterQuery != null) {
            solrQuery.setFilterQueries(filterQuery);//fq查询参数

        }

        if (filterQuery != null) {
            solrQuery.setFilterQueries(filterQuery);
        }
        if (sort != null) {
            //添加排序条件
            Set<String> sortkey = sort.keySet();
            for (String key : sortkey) {
                if (sort.get(key).equals(SortType.ASC.value())) {
                    solrQuery.setSort(key, SolrQuery.ORDER.asc);
                } else {
                    solrQuery.setSort(key, SolrQuery.ORDER.desc);
                }
            }
        }

        //分页
        if (startIndex != null && pageSize != null) {
            solrQuery.setStart((startIndex - 1) * pageSize);
            solrQuery.setRows(pageSize);
        }

        if (facetFileds != null && facetFileds.length > 0) {
            solrQuery.setFacet(true);
            solrQuery.setFacetMinCount(1);
            solrQuery.addFacetField(facetFileds);
        }


        QueryResponse response = solrClientUtil.getReadServer(corename).query(solrQuery);

        List<T> doc = response.getBeans(clazz);
        nums = response.getResults().getNumFound();
        return doc;
    }

}