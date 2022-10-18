package com.example;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest(classes = MybatisPlusApplication9101.class)
public class DeleteTest {
  @Autowired
  private UserMapper userMapper;
  @Test
  public void deleteById() {
    int i = userMapper.deleteById(2L);
    System.out.println(i);
  }
  @Test
  public void testDeleteByMap() {
    Map<String, Object> columnMap = new HashMap<>();
    columnMap.put("age",17);
    columnMap.put("username","张三");
    //将columnMap中的元素设置为删除的条件，多个之间为and关系
    int result = this.userMapper.deleteByMap(columnMap);
    System.out.println("result = " + result);
  }

  @Test
  public void testDeleteByMap2() {
    User user = new User();
    user.setAge(17);
    user.setUsername("王五");
    //将实体对象进行包装，包装为操作条件
    QueryWrapper<User> wrapper = new QueryWrapper<>(user);
    int result = this.userMapper.delete(wrapper);//慎用！！如果配置文件添加field-strategy=ignore,是根据实体的所有字段where判断的，实体没赋值的字段也会带上
    System.out.println("result = " + result);
  }

  /**
   * @Description 根据id批量删除
   * @return void
   * @date   2021/9/13 15:52
   * @Author huangqiqi
   */
  @Test
  public void testBatchIds() {
    //根据id集合批量删除
    int result = this.userMapper.deleteBatchIds(Arrays.asList(2L,3L,5L));
    System.out.println("result = " + result);
  }

}
