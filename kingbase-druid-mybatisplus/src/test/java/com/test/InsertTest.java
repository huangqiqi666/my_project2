package com.test;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.test.entity.User;
import com.test.mapper.UserMapper;
import com.test.service.IUserService;
import com.test.util.RandomInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
public class InsertTest {
  @Autowired
  private UserMapper userMapper;
  @Autowired
  private IUserService userService;

  @Test
  public void insert() {

    List<User> users = new ArrayList<User>() {{
      add(User.builder().username(RandomInfo.getRandomBoyName()).age(RandomInfo.getRandomAge(10, 50)).build());
      add(User.builder().username(RandomInfo.getRandomBoyName()).age(RandomInfo.getRandomAge(10, 50)).build());
      add(User.builder().username(RandomInfo.getRandomBoyName()).age(RandomInfo.getRandomAge(10, 50)).build());
      add(User.builder().username(RandomInfo.getRandomBoyName()).age(RandomInfo.getRandomAge(10, 50)).build());
      add(User.builder().username(RandomInfo.getRandomBoyName()).age(RandomInfo.getRandomAge(10, 50)).build());
    }};
    //单个新增
//    userMapper.insert(users);
    //批量保存
    userService.saveBatch(users);

    QueryWrapper<User> wrapper = new QueryWrapper<>();
    //TODO:1.返回全部字段：select(User.class,x->true)
    //2.指定字段
    //3.根据条件过滤字段

    // 对应的sql:SELECT id,username,age,password,type FROM "user" limit 3
//    wrapper.select("user_name","age").last("limit 3");//指定字段
    wrapper.select(User.class,x->true).last("limit 10");//返回全部字段
//    wrapper.select(User.class,x-> x.getProperty().toString().startsWith("a")).last("limit 3");//过滤字段
//    List<User> list = userMapper.selectList(null);
    List<User> list = userMapper.selectList(wrapper);
    System.out.println("list:"+ JSON.toJSONString(list));

  }


}
