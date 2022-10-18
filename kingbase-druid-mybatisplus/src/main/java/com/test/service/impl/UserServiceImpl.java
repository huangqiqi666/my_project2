package com.test.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.entity.User;
import com.test.mapper.UserMapper;
import com.test.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * @Classname UserServiceImpl
 * @Description TODO:参考：https://blog.csdn.net/leisure_life/article/details/98976565
 * @Date 2022/10/14 10:46
 * @Created by huangqiqi
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
