package com.comment.model;

import org.apache.solr.client.solrj.beans.Field;

public class Order {
    @Field("orderId")
    private String orderid;
    @Field("cinemaName")
    private String cinemaname;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getCinemaname() {
        return cinemaname;
    }

    public void setCinemaname(String cinemaname) {
        this.cinemaname = cinemaname;
    }
}
