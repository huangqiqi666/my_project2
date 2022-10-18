package com.hikvision.myproject.stream;

import com.hikvision.myproject.stream.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Classname StreamTest
 * @Description
 * @Date 2022/8/11 15:48
 * @Created by huangqiqi
 */
@SpringBootTest(classes = WebApp.class)
@RunWith(SpringRunner.class)
public class StreamTest {

    @Test
    public void stream(){

        //List：字段转换为实体
        List<String>ids= Arrays.asList("11","22","33","44","55");
        List<User> users = ids.stream().map(x -> {
            User user = new User(x);
            return user;
        }).collect(Collectors.toList());
        System.out.println(users);

        //集合转map
        Map<String, User> map = users.stream().collect(Collectors.toMap(User::getId, x -> x));
        System.out.println("map:"+map);
        //集合转set
        Set<User> set = users.stream().collect(Collectors.toSet());
        System.out.println("set:"+set);


        //flatMap
        List<String>terms=Arrays.asList("zhang shan","li shi","wang wu");
        List<String> list = terms.stream().flatMap(x -> Arrays.stream(x.split(" "))).collect(Collectors.toList());
        System.out.println(list);
        //TODO:一旦stream执行了终止操作，stream流就关闭了，再调用stream就会报错

        //生成拼接字符串
        String s = ids.stream().collect(Collectors.joining(","));
        System.out.println("逗号分隔拼接的字符串："+s);

        //批量数学操作
        List<Integer>integers=Arrays.asList(10,20,30,40,50);
        Double aver = integers.stream().collect(Collectors.averagingInt(x -> x));
        System.out.println("平均值："+aver);
        IntSummaryStatistics summaryStatistics = integers.stream().collect(Collectors.summarizingInt(x -> x));
        System.out.println("统计信息："+summaryStatistics);

        //stream特性：中间管道不管有多少步骤都不会立即执行，只有遇到终止管道操作时才会开始执行，可以避免中间一些不必要的操作消耗
        //弊端：不好debug


    }
}
