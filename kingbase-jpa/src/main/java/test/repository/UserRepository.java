package test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.repository.entity.User;

/**
 * @Classname UserRepository
 * @Description TODO
 * @Date 2022/10/11 13:39
 * @Created by huangqiqi
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findTopByAge(Integer age);
}
