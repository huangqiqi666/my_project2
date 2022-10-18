import com.test.arthas.service.impl.TestServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Classname Mytest
 * @Description arthas idea插件的使用
 * 参考：https://blog.csdn.net/u012881904/article/details/103865802
 * @Date 2022/10/17 15:38
 * @Created by huangqiqi
 */
@SpringBootTest(classes = com.test.arthas.App.class)
public class Mytest {
    @Autowired
    TestServiceImpl testService;

    @Test
    void test(){
        //watch Mytest test '{params,returnObj,throwExp}'  -n 5  -x 3
        testService.a();
    }


}
