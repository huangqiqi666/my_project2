package com.test.kingbasedemo;

import com.alibaba.fastjson.JSON;
import com.test.kingbasedemo.service.MenuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KingbaseDemoApplicationTests {
    @Autowired
    private MenuService menuService;

    @Test
    void insert(){
        menuService.addMenu();
    }

    @Test
    void contextLoads() {
        System.out.println( "查询全部结果："+ JSON.toJSONString(menuService.queryAllMenu()) );
    }

}
