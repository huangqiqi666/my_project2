package com.test.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//mybatisPlus映射的表名
//@TableName("user")
@TableName("\"user\"")//TODO:人大金仓表的sql中表名需要双引号,不然会报错！！

public class User {
    //主键自增长(IdType.AUTO:数据库ID自增；IdType.NONE、IdType.INPUT：insert前自行set主键值；
    // IdType.ASSIGN_ID：默认雪花算法,支持Long和String；IdType.UUID:String)
    @TableId(type = IdType.AUTO)
//    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    @TableField("username")//解决字段名称不一样（非驼峰）
    private String username;
    private Integer age;
    @TableField(select = false)//指定该字段不加入查询
    private String password;
    private String type;
    @TableField(exist = false)//该字段在数据表中不存在
    private String other;
}
