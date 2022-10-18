package com.hikvision.myproject.es.service;

import com.hikvision.myproject.es.entity.es.Goods;
import com.hikvision.myproject.es.repository.es.GoodsRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService { 
  
    @Resource
    private GoodsRepository goodsRepository;
 
    public void saveData() {
            List<Goods> goodsList = new ArrayList<>();
            goodsRepository.saveAll(goodsList);
    }
 
}