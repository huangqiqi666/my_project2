package com.test.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.test.entity.User;
import org.springframework.stereotype.Service;

/**
 * @Classname IUserService
 * @Description mybatisplus提供的IService里有批量保存方法
 * @Date 2022/10/14 10:44
 * @Created by huangqiqi
 */
@Service
public interface IUserService  extends IService<User> {

}
