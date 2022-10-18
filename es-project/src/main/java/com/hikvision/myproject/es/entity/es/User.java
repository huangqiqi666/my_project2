package com.hikvision.myproject.es.entity.es;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @Classname User
 * @Description TODO
 * @Date 2022/8/25 16:03
 * @Created by huangqiqi
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document(indexName = "user",shards = 3)
public class User {

    @Id
    @Field(type = FieldType.Text)
    private String id;
    @Field(type = FieldType.Text)
    private String name;
    @Field(type = FieldType.Integer)
    private Integer age;
    @CreatedDate
    @Field(type = FieldType.Date)
    private Date createTime;
    @LastModifiedDate
    @Field(type = FieldType.Date)
    private Date updateTime;

}

