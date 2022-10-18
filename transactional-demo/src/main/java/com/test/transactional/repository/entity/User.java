package com.test.transactional.repository.entity;

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
//@Table(name = "user")
@Table(name = "\"user\"")  //TODO:需要添加双引号!
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
