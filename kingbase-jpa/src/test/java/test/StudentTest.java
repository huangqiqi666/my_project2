package test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import test.repository.StudentRepository;
import test.repository.entity.Student;

/**
 * @Classname StudentTest
 * @Description TODO
 * @Date 2022/10/13 14:48
 * @Created by huangqiqi
 */
@SpringBootTest
public class StudentTest {
    @Autowired
    private StudentRepository studentRepository;

    @Test
    void insert(){
        studentRepository.saveAndFlush( Student.builder().age(20).name("王五").build());
    }
}
