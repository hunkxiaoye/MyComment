package com.comment.common;


import com.alibaba.fastjson.JSON;
import com.comment.common.Solr.SolrClientUtil;
import com.comment.common.Solr.SortType;
import javafx.scene.control.TableColumn;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
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

    public void add(Object model, String corname) throws IOException, SolrServerException {
        solrClientUtil.getReadServer(corname).addBean(model);
    }

    public <T> List<T> selectquery(String query, String corename, Map<String, String> sort,
                                   Integer startIndex, Integer pageSize, String[] facetFileds,
                                   String filterQuery, Class<T> clazz) throws IOException, SolrServerException {
        SolrQuery solrQuery = new SolrQuery();//封装查询参数
        if (query == null || query.equals("")) {
            solrQuery.setQuery("*:*");
        } else {
            solrQuery.setQuery(query);
        }
        //Test
        //solrQuery.addField("orderId");
        //solrQuery.addField("cinemaName");
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
        return doc;
    }

    public void querydel(String idname, Object idvalue, String corname)
            throws IOException, SolrServerException {
        String deltxt = idname + ":" + String.valueOf(idvalue);
        solrClientUtil.getReadServer(corname).deleteByQuery(deltxt);
    }

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

        solrClientUtil.getReadServer(corname).deleteByQuery(deltxt);

    }

    public void delbyid(Object id, String corname) throws IOException, SolrServerException {
        solrClientUtil.getReadServer(corname).deleteById(String.valueOf(id));
    }

    public void mdelbyid(List<Object> ids, String corname) throws IOException, SolrServerException {
        List<String> list = new ArrayList<>();
        for (Object obj : ids) {
            list.add(String.valueOf(obj));
        }

        solrClientUtil.getReadServer(corname).deleteById(list);
    }
}
