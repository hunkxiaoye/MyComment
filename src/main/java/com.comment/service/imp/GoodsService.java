package com.comment.service.imp;

import com.comment.dao.GoodsDao;
import com.comment.model.Goods;
import com.comment.service.inf.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service("GoodsService")
public class GoodsService implements IGoodsService {
    @Autowired
    private GoodsDao goodsDao;
    public void add(Goods goods){
        goodsDao.add(goods);
    }

    public ArrayList<Goods> findGoodsAll(){
        return  goodsDao.findGoodsAll();
    }

}
