package com.example;

import com.example.entity.User;
import com.example.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest(classes = MybatisPlusApplication9101.class)
public class InsertTest {
  @Autowired
  private UserMapper userMapper;
  @Test
  public void testSelect() {
    List<User> userList = userMapper.selectList(null);
    for (User user : userList) {
      System.out.println(user);
    }
  }


}
