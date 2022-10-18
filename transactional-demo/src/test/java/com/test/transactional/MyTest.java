package com.test.transactional;

import com.alibaba.fastjson.JSON;
import com.test.transactional.repository.UserRepository;
import com.test.transactional.repository.entity.User;
import com.test.transactional.service.TestServiceImpl;
import com.test.transactional.service.TestServiceImpl2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Classname MyTest
 * @Description TODO
 * @Date 2022/10/17 10:09
 * @Created by huangqiqi
 */
@SpringBootTest
public class MyTest {
    @Autowired
    TestServiceImpl testService;
    @Autowired
    TestServiceImpl2 testServiceImpl2;
    @Autowired
    UserRepository userRepository;

    //TODO:1.测试一个类中的不同方法的事务
    @Test
    void testOneClassTransaction(){
        //先删除一下数据库
        userRepository.deleteAll();
        //测试事务
        testService.method1();
        System.out.println(JSON.toJSONString(userRepository.findAll()));
    }

    //TODO: 2.测试不同类的方法之间的事务
    @Test
    void testMultipleClassTransaction(){
        //先删除一下数据库
        userRepository.deleteAll();
        //测试事务
        testService.method3();
    }

    //测试testMultipleClassTransaction2的deleteAll是否回滚事务（先新增一条数据，然后执行这个方法。也会回滚）
    @Test
    @Transactional(rollbackFor = Exception.class)
    void testMultipleClassTransaction2(){
        //先删除一下数据库
        userRepository.deleteAll();
        //测试事务
        testService.method3();
        System.out.println(JSON.toJSONString(userRepository.findAll()));
    }


    @Test
    void insert(){
        userRepository.save(User.builder().age(10).name("张三").build());
    }

    @Test
    void selectALL(){
        System.out.println(userRepository.findAll());
    }

    @Test
    void delete(){
        userRepository.deleteAll();
    }
}
