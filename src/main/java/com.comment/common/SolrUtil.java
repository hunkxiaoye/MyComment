package com.comment.common;


import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class SolrUtil {

    private static final Logger log = LoggerFactory.getLogger(SolrUtil.class);

    @Autowired
   private SolrClientUtil solrClientUtil;

    public void add(Object model,String corname) throws IOException, SolrServerException {
        solrClientUtil.getReadServer(corname).addBean(model);
    }

public SolrDocumentList select(String query ,String corname)throws IOException, SolrServerException{
    SolrQuery solrQuery = new SolrQuery();//封装查询参数
    if (query==null||query.equals(""))
        solrQuery.setQuery("*:*");
    else
        solrQuery.setQuery(query);
    solrQuery.addField("orderId");
    solrQuery.addField("cinemaName");
    solrQuery.setRows(10);//每页显示条数

    QueryResponse response = solrClientUtil.getReadServer(corname).query(solrQuery);
    SolrDocumentList documents = response.getResults();
    return documents;
}
//    public <T> T createquery(Map<String, Object> property, Map<String, String> sort,
//                             Integer startIndex, Integer pageSize, String[] facetFileds,
//                             String filterQuery) {
//        SolrQuery sq = new SolrQuery();
//        sq.setQuery("*:*");
//
//        if (filterQuery != null) {
//            sq.setFilterQueries(filterQuery);
//        }
//        if (property != null) {
//            //添加查询条件
//            property.entrySet().forEach(pro -> {
//                Object o = pro.getValue();
//                String key = pro.getKey();
//                if (StringUtils.isEmpty(o)) return;
//                if (o.getClass().isArray()) {
//                    StringBuilder sb = new StringBuilder();
//                    Object[] values = (Object[]) o;
//                    for (Object value : values) {
//                        if (sb.length() > 0) sb.append(" OR ");
//                        sb.append(key + ":" + value);
//                    }
//                    sq.addFilterQuery(sb.toString());
//                } else {
//                    sq.addFilterQuery(key + ":" + o);
//                }
//            });
//        }
//
//        if (sort != null) {
//            //添加排序条件
//            Set<String> sortkey = sort.keySet();
//            for (String key : sortkey) {
//                if (sort.get(key).equals(TableColumn.SortType..value())){
//                    sq.setSort(key, SolrQuery.ORDER.asc);
//                }else{
//                    sq.setSort(key, SolrQuery.ORDER.desc);
//                }
//            }
//        }
//
//        //分页
//        if (startIndex != null && pageSize != null) {
//            sq.setStart((startIndex - 1) * pageSize);
//            sq.setRows(pageSize);
//        }
//
//        if (facetFileds != null && facetFileds.length > 0) {
//            sq.setFacet(true);
//            sq.setFacetMinCount(1);
//            sq.addFacetField(facetFileds);
//        }
//        return sq;
//    }

    public void del(String idname, Long idvalue,String corname)
            throws IOException, SolrServerException
    {
        String deltxt = idname + ":" + String.valueOf(idvalue);
        solrClientUtil.getReadServer(corname).deleteByQuery(deltxt);
    }
}
