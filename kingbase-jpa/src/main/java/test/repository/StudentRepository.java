package test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.repository.entity.Student;

/**
 * @Classname StudentRepository
 * @Description TODO
 * @Date 2022/10/13 14:47
 * @Created by huangqiqi
 */
@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
}
