package com.comment.common.Solr;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Component
public class SolrClientUtil {

    private static volatile Map<String, HttpSolrClient> solrClients = new HashMap<>();

    private static final Object _lock = new Object();

    @Value("${writesolrUrl}")
    private String writeClientsurl;
    @Value("${readsolrUrl}")
    private String redaClientsurl;

    public HttpSolrClient getWriteServer(String coreName) throws IOException {
        String key = coreName + "_Write";
        if (!solrClients.containsKey(coreName)) {
            synchronized (_lock) {
                if (!solrClients.containsKey(coreName)) {
                    String writeServerUrl = writeClientsurl + coreName;
                    HttpSolrClient solrClient = new HttpSolrClient.Builder(writeServerUrl).build();
                    solrClients.put(key, solrClient);
                }
            }
        }

        return solrClients.get(key);

    }

    public HttpSolrClient getReadServer(String coreName) {
        String key = coreName + "_Read";
        if (!solrClients.containsKey(coreName)) {
            synchronized (_lock) {
                if (!solrClients.containsKey(coreName)) {
                    String ReadServerUrl = redaClientsurl + coreName;
                    HttpSolrClient solrClient = new HttpSolrClient.Builder(ReadServerUrl).build();
                    solrClients.put(key, solrClient);
                }
            }
        }

        return solrClients.get(key);

    }
}
