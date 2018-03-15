package com.comment.model;

import org.apache.solr.client.solrj.beans.Field;

public class Order {
    @Field
    private Long orderId;
    @Field
    private String cinemaName;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }
}
