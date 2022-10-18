package test.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.repository.UserRepository;
import test.repository.entity.User;

import java.util.List;

/**
 * @Classname UserController
 * @Description TODO
 * @Date 2022/10/11 13:40
 * @Created by huangqiqi
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    public String getAllUser(){
        List<User> all = userRepository.findAll();
        System.out.println("all:"+JSON.toJSONString(all));
        return JSON.toJSONString(all);

    }
}
