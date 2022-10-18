package test.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;

import javax.persistence.*;

/**
 * @Classname Test
 * @Description TODO
 * @Date 2022/10/13 14:44
 * @Created by huangqiqi
 */
@Data
@Table(name = "\"student\"")//TODO:国产人大金仓的数据库表名都必须带上双引号！！不然报错！！还需要注意生成的表名是否有双引号！
//@Table(name = "student")

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer age;
}
