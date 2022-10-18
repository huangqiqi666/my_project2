package com.example;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description 推荐使用LambdaUpdateWrapper更新（JDK8）！！！！！！！！！！
 * @date   2021/9/13 16:52
 * @Author huangqiqi
 */
@SpringBootTest(classes = MybatisPlusApplication9101.class)
public class UpdateTest {
  @Autowired
  private UserMapper userMapper;

  private int id=1;

  /**
   * @Description 这种更新会导致数据的丢失，因为我只想更新部分的字段
   * @param
   * @return void
   * @date   2021/9/13 16:47
   * @Author huangqiqi
   */
  @Test
  public void testUpdateById(){
    User employee = new User();
    employee.setId(1L);
    employee.setUsername("dahua");
    int i = userMapper.updateById(employee);//更新所有字段！！慎用
    System.out.println("修改条数："+i);
  }
  @Test
  public void testUpdateById2(){
    //先查询，再更新
    User employee = userMapper.selectById(1L);
    employee.setUsername("xiaolin");
    userMapper.updateById(employee);
  }

  //使用建议:知道id，并且所有更新使用updateById; 部分字段更新，使用update
  // 方法一：使用QueryWrapper
  @Test
  public void testUpdate() {
    User user = new User();
    user.setAge(23); //更新的字段
    //更新的条件
    QueryWrapper<User> wrapper = new QueryWrapper<>();
    wrapper.eq("id", 1);
    //执行更新操作
    int result = this.userMapper.update(user, wrapper);//慎用！！如果配置文件添加field-strategy=ignore,是根据实体的所有字段set，实体没赋值的字段也会带上
    System.out.println("result = " + result);
  }

  //方法二： 通过UpdateWrapper进行更新(推荐！)
  @Test
  public void testUpdateWrapper(){
    //更新的条件以及字段
    UpdateWrapper<User> wrapper=new UpdateWrapper<>();
    wrapper.eq("id",1).set("age",23);//只更新指定的set字段（即只更新age）
//    wrapper.setSql("name='xiaolin'");//也可以直接注入sql
    //执行更新操作
    int result=this.userMapper.update(null,wrapper);
    System.out.println("result = "+result);
  }

  /**
   * 通过LambdaUpdateWrapper进行更新(推荐！！)
   * 推荐使用LambdaUpdateWrapper更新。
   */
  @Test
  public void testLambdaUpdateWrapper(){
    //更新的条件以及字段
    LambdaUpdateWrapper<User > wrapper=new LambdaUpdateWrapper<>();
    wrapper.eq(User::getId,id).set(User::getAge,27);
    //执行更新操作
    int result=this.userMapper.update(null,wrapper);
    System.out.println("result = "+result);
  }

}
