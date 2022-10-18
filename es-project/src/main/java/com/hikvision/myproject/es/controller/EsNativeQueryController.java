package com.hikvision.myproject.es.controller;

import com.alibaba.fastjson.JSON;
import com.hikvision.myproject.es.service.EsNativeQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Classname EsNativeQueryController
 * @Description TODO
 * @Date 2022/8/26 10:43
 * @Created by huangqiqi
 */
@RestController(value = "/nativeQuery")
public class EsNativeQueryController {
    @Autowired
    EsNativeQueryService esNativeQueryService;

    @GetMapping("/searchCategoryList")
    public void searchCategoryList(Map searchMap){
        List<String> list = esNativeQueryService.searchCategoryList(searchMap);
        System.out.println(JSON.toJSONString(list));
    }

    @GetMapping("/testSearchByTemplate")
    public void testSearchByTemplate(){
        esNativeQueryService.testSearchByTemplate();
    }

    @GetMapping("/testNativeQuery")
    public void testNativeQuery(){
        try {
            esNativeQueryService.testNativeQuery();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/searchDocument")
    public void searchDocument(){
        try {
            esNativeQueryService.searchDocument();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
