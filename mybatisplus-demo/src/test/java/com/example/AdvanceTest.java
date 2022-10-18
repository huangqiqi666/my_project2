package com.example;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Description mybatisPlus高级用法
 * @date   2021/9/13 17:00
 * @Author huangqiqi
 */
@SpringBootTest(classes = MybatisPlusApplication9101.class)
public class AdvanceTest {
  @Autowired
  private UserMapper userMapper;
  //列投影
  // 需求：查询所有员工， 返回员工name， age列
  @Test
  public void testQuery(){
    QueryWrapper<User> wrapper = new QueryWrapper<>();
    wrapper.select("username", "age");//指定返回的列
    List<User> users = userMapper.selectList(wrapper);
    System.out.println(users);
  }

  // 需求：查询所有员工， 返回员工以a字母开头的列
  @Test
  public void testQuery2(){
    QueryWrapper<User> wrapper = new QueryWrapper<>();
    wrapper.select(User.class, tableFieldInfo->tableFieldInfo.getProperty().startsWith("a"));//筛选以a开头的列
    List<User> users = userMapper.selectList(wrapper);
    System.out.println(users);
  }

  /**
   * @Description 排序
   * @param
   * @return void
   * @date   2021/9/13 17:26
   * @Author huangqiqi
   */
  @Test
  public void testQuery5(){
//    QueryWrapper<User> wrapper = new QueryWrapper<>();
    LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
    //排序：查询所有员工信息按age正序排， 如果age一样， 按id倒序排
    wrapper.orderByAsc(User::getId);//排序
    wrapper.orderByDesc(User::getAge);//排序
    List<User> users = userMapper.selectList(wrapper);
    System.out.println(users);
  }

  /**
   * @Description OR：主动调用or表示紧接着下一个方法不是用and连接!(不调用or则默认为使用and连接)
   * @date   2021/9/13 17:24
   * @Author huangqiqi
   */
  @Test
  public void testQuery24(){
    QueryWrapper<User> wrapper = new QueryWrapper<>();
    //  查询age = 18 或者 name=xiaolin 或者 id =1 的用户
    wrapper.eq("age", 18)
            .or()
            .eq("name", "xiaolin")
            .or()
            .eq("id", 1L);
    userMapper.selectList(wrapper);

  }

  /**
   * @Description 分组查询group by
   * @date   2021/9/13 17:25
   * @Author huangqiqi
   */
  @Test
  public void testQuery22(){
    QueryWrapper<User> wrapper = new QueryWrapper<>();
    // 需求： 以部门id进行分组查询，查每个部门员工个数
    wrapper.groupBy("type");
    wrapper.select("dept_id", "count(id) count");
    userMapper.selectMaps(wrapper);
  }

  /**
   * @Description having用法
   * @param
   * @return void
   * @date   2021/9/14 9:50
   * @Author huangqiqi
   */
  @Test
  public void testQuery23(){
    QueryWrapper<User> wrapper = new QueryWrapper<>();
    // 需求： 以部门id进行分组查询，查每个部门员工个数， 将大于3人的部门过滤出来
    wrapper.groupBy("dept_id")
            .select("dept_id", "count(id) count")
            //.having("count > {0}", 3)
            .having("count >3");
    userMapper.selectMaps(wrapper);
  }

  /**
   * @Description 链式查询LambdaQueryChainWrapper
   * @param
   * @return void
   * @date   2021/9/14 10:14
   * @Author huangqiqi
   */
  @Test
  public void testLambdaQueryChainWrapper(){
//    LambdaQueryChainWrapper<User> wrapper = new LambdaQueryChainWrapper<>(userMapper);
//    User one = wrapper.eq(User::getId, 1L).lt(User::getAge, 20).one();
    User one =new LambdaQueryChainWrapper<>(userMapper).eq(User::getId, 1L).lt(User::getAge, 20).one();
    System.out.println(one);

  }








}
