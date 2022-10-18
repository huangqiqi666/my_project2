package com.example.controller;

import com.example.entity.User;
import com.example.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
  @Autowired
  private UserMapper userMapper;
  @GetMapping(value = "/selectAll")
  public void testSelect() {
    List<User> userList = userMapper.selectList(null);
    for (User user : userList) {
      System.out.println(user);
    }
  }

}