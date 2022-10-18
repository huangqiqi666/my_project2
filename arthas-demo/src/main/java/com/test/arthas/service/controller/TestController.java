package com.test.arthas.service.controller;

import com.test.arthas.service.impl.TestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname TestController
 * @Description TODO
 * @Date 2022/10/17 17:16
 * @Created by huangqiqi
 */
@RestController
public class TestController {

    @Autowired
    TestServiceImpl testService;

    @GetMapping("/test")
    public String test(){
       return testService.a();
    }

 @GetMapping("/test2")
    public Integer test2(@RequestParam String name){
       return testService.exception(name);
    }

}
