package test;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import test.repository.UserRepository;
import test.repository.entity.User;

/**
 * @Classname MyTest
 * @Description TODO
 * @Date 2022/10/11 13:44
 * @Created by huangqiqi
 */
@SpringBootTest
public class MyTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void insert(){



//        userRepository.saveAndFlush(User.builder().age(10).name("张三").build());
        System.out.println("插入成功");
    }
    @Test
    public void select(){

        User user = userRepository.findTopByAge(10);
        System.out.println("user:"+JSON.toJSONString(user));
        System.out.println(JSON.toJSONString(userRepository.findAll() ));
    }
}
