package com.test.transactional.service;

import com.test.transactional.repository.UserRepository;
import com.test.transactional.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Classname TestServiceImpl
 * @Description 测试一个类里的不同方法的事务!
 * @Date 2022/10/17 9:50
 * @Created by huangqiqi
 */
@Service
public class TestServiceImpl2 {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private TestServiceImpl2 testService;


    @Transactional(rollbackFor = Exception.class)
    public void method(){
        //新增王五
        userRepository.save(User.builder().age(80).name("徐六").build());
        System.out.println("方法插入Db成功");
        //模拟异常
        int i=1/0;
    }


}
