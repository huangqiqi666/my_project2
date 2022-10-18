package com.test.transactional.service;

import com.test.transactional.repository.UserRepository;
import com.test.transactional.repository.entity.User;
import org.springframework.aop.framework.AopContext;
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
public class TestServiceImpl {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private TestServiceImpl testService;
    @Autowired
    private TestServiceImpl2 testServiceImpl2;


    @Transactional(rollbackFor = Exception.class)
//    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void method1(){
        userRepository.save(User.builder().age(10).name("张三").build());
        System.out.println("方法1插入成功");
        //TODO:直接调用（事务不生效。因为需要代理类才生效）
//        method2();
        /*TODO:使事务生效的方法：3种*/
        //1.自己注入自己
//        testService.method2();

        //2.从ApplicationContext获取bean，再调用方法
//        TestServiceImpl testServiceImpl =(TestServiceImpl) applicationContext.getBean("testServiceImpl");
//        testServiceImpl.method2();

        //3.AopContext获取
        // 第一步：启动类@EnableAspectJAutoProxy(exposeProxy=true)//开启“代理暴露”，代理类就会被设置到AopContext中
        //第二步：AopContext.currentProxy()获取当前的代理对象
        TestServiceImpl testServiceImpl2 =(TestServiceImpl) AopContext.currentProxy();
        testServiceImpl2.method2();
    }

    @Transactional(rollbackFor = Exception.class)
//    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public void method2(){
        //新增王五
        userRepository.save(User.builder().age(60).name("王五").build());
        System.out.println("方法2插入Db成功");
        //模拟异常
        int i=1/0;
    }


    /**
     * @Description 测试事务回滚，只要有异常就会回滚（无论是否捕获testServiceImpl2.method()）
     * @param
     * @return void
     * @date   2022/10/17 15:20
     * @Author huangqiqi
     */
    @Transactional(rollbackFor = Exception.class)
    public void method3() {
        userRepository.save(User.builder().age(10).name("张三").build());
        System.out.println("方法3插入成功");
        //TODO:只要收到收到一次，就会执行改方法内的回滚！
        //1.调用另一个类的方法（模拟异常。todo:也会回滚！）
//        testServiceImpl2.method();
        //2.测试捕获异常，是否事务会回滚（todo:也会回滚！）
        try {
            testServiceImpl2.method();
        } catch (Exception e) {
            System.out.println("捕获异常");
        }
    }
}
