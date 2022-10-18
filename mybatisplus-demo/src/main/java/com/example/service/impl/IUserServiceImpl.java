package com.example.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.service.IUserService;
import org.springframework.stereotype.Service;


/**
 * @Classname IUserServiceImpl
 * @Description TODO
 * @Date 2021/9/14 14:25
 * @Created by huangqiqi
 */
@Service
public class IUserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
}
