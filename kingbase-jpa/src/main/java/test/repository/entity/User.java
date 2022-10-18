package test.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @Classname User
 * @Description TODO
 * @Date 2022/10/11 13:37
 * @Created by huangqiqi
 */
@Data
@Table(name = "\"user\"")//TODO:1.国产人大金仓的数据库表名都必须带上双引号！！不然报错！！2.生成的表名会去掉双引号，执行sql会带上双引号
//@Table(name = "user")

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer age;

}
