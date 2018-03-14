package com.comment.dao;

import com.comment.model.Goods;

import java.util.ArrayList;

public interface GoodsDao {
    ArrayList<Goods> findGoodsAll();

    void add(Goods goods);
}
