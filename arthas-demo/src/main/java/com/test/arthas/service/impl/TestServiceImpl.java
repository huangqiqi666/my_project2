package com.test.arthas.service.impl;

import cn.hutool.core.thread.ThreadUtil;
import org.springframework.stereotype.Service;

/**
 * @Classname TestImpl
 * @Description TODO
 * @Date 2022/10/17 15:54
 * @Created by huangqiqi
 */
@Service
public class TestServiceImpl {


    public String a(){
        ThreadUtil.sleep(1000);
        System.out.println("执行方法a");
        b();
        return "成功";
    }
    public void b(){
        ThreadUtil.sleep(2000);
        System.out.println("执行方法1");
        c();

    }
    public void c(){
        ThreadUtil.sleep(3000);
        System.out.println("执行方法1");
    }

    public Integer exception(String a) {
        System.out.println(a);
        int i = 0;
        try {
            i = 1 / 0;
        } catch (Exception e) {
            System.out.println("执行方法exception异常");
        }
        return i;
    }
}
