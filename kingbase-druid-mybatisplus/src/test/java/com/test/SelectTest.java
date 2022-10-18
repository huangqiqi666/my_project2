package com.test;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.test.entity.User;
import com.test.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

/**
 * @Description 推荐使用LambdaUpdateWrapper更新（JDK8）！！！！！！！！
 * @date   2021/9/13 16:53
 * @Author huangqiqi
 */

@SpringBootTest
public class SelectTest {
  @Autowired
  private UserMapper userMapper;
  @Test
  public void testSelect() {
    List<User> userList = userMapper.selectList(null);
    for (User user : userList) {
      System.out.println(user);
    }
  }


  @Test
  public void testSelectById() {
    //根据id查询数据
    User user = this.userMapper.selectById(1L);
    System.out.println("result = " + user);
  }

  /**
   * @Description 批量查询
   * @param
   * @return void
   * @date   2021/9/13 16:35
   * @Author huangqiqi
   */
  @Test
  public void testSelectBatchIds() {
    //根据id集合批量查询
    List<User> users = userMapper.selectBatchIds(Arrays.asList(1L, 4L, 8L));
    for (User user : users) {
      System.out.println(user);
    }
  }

  @Test
  public void testSelectOne() {
    QueryWrapper<User> wrapper = new QueryWrapper<User>();
    wrapper.eq("username", "张三");
    //根据条件查询一条数据，如果结果超过一条会报错
    User user = userMapper.selectOne(wrapper);
    System.out.println(user);
  }

  @Test
  public void testSelectCount() {
    QueryWrapper<User> wrapper = new QueryWrapper<User>();
    wrapper.gt("age", 23); //年龄大于23岁
    Integer count = userMapper.selectCount(wrapper);
    System.out.println("count = " + count);
  }

  /**
   * @Description 查询list
   * @param
   * @return void
   * @date   2021/9/13 16:35
   * @Author huangqiqi
   */
  @Test
  public void testSelectList() {
    QueryWrapper<User> wrapper = new QueryWrapper<User>();
    wrapper.gt("age", 23); //年龄大于23岁
    //根据条件查询数据
    List<User> users = userMapper.selectList(wrapper);
    for (User user : users) {
      System.out.println("user = " + user);
    }
  }

  /**
   * @Description 分页查询
   * @param
   * @return void
   * @date   2021/9/13 16:34
   * @Author huangqiqi
   */
  @Test
  public void testSelectPage() {
    QueryWrapper<User> wrapper = new QueryWrapper<User>();
//    wrapper.gt("age", 20); //年龄大于20岁
    wrapper.ge("age", 20); //年龄大于等于20岁
    Page<User> page = new Page<>(2,1);
//    Page<User> page = new Page<>(1,10);
    //根据条件查询数据

    IPage<User> iPage = userMapper.selectPage(page, wrapper);
    System.out.println("数据总条数：" + iPage.getTotal());
    System.out.println("总页数：" + iPage.getPages());
    List<User> users = iPage.getRecords();
    for (User user : users) {
      System.out.println("user = " + user);
    }
  }



  /**
   * @Description LambdaQueryWrapper查询() advanced
   * @date   2021/9/13 16:58
   * @Author huangqiqi
   */
  @Test
  public void testQuery3(){
    LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
    //查询name=xiaolin， age=18的用户
    wrapper.eq(User::getUsername, "李四").eq(User::getAge, 17);
    List<User> users = userMapper.selectList(wrapper);
    System.out.println(users);
  }

}
