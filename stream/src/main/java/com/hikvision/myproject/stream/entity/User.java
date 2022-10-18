package com.hikvision.myproject.stream.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname User
 * @Description TODO
 * @Date 2022/8/11 15:52
 * @Created by huangqiqi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String id;
    private String name;
    private String age;

    public User(String id) {
        this.id = id;
    }


}
