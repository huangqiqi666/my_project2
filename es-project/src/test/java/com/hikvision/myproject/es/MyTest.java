package com.hikvision.myproject.es;


import com.hikvision.myproject.es.entity.es.Goods;
import com.hikvision.myproject.es.repository.es.GoodsRepository;
import com.hikvision.myproject.es.service.EsNativeQueryService;
import com.hikvision.myproject.es.service.SearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Classname MyTest
 * @Description TODO
 * @Date 2022/8/3 11:28
 * @Created by huangqiqi
 */
@SpringBootTest()
@RunWith(SpringRunner.class)
public class MyTest {
    @Autowired
    private SearchService searchService;
    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private EsNativeQueryService esNativeQueryService;

    @Test
    public void save(){
        List<Goods>goodsList=new ArrayList<>();
        Goods goods = Goods.builder().id(1111L).name("康师傅矿泉水").price(10)
                .createTime(new Date())
                .build();
        goodsList.add(goods);
      goodsRepository.saveAll(goodsList);
    }

    @Test
    public void nativeQueryTest(){
        try {
            esNativeQueryService.testNativeQuery();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
