package com.test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.test.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Classname UserMapper
 * @Description TODO
 * @Date 2022/10/13 13:37
 * @Created by huangqiqi
 */
@Mapper
public interface UserMapper  extends BaseMapper<User> {


}
