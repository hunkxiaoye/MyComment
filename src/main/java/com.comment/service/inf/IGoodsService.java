package com.comment.service.inf;

import com.comment.model.Goods;

import java.util.ArrayList;

public interface IGoodsService {
    ArrayList<Goods> findGoodsAll();

    void add(Goods goods);
}
