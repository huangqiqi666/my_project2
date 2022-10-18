package com.hikvision.myproject.es.entity.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @Classname Book
 * @Description TODO
 * @Date 2022/8/24 11:23
 * @Created by huangqiqi
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {
    @Id
    @Field(type = FieldType.Long)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Field(analyzer="ik_max_word")
    private String title;
    @Field(analyzer="ik_max_word")
    private String author;
    @Field(type = FieldType.Double)
    private Double price;
    @Field(type = FieldType.Date,format = DateFormat.basic_date_time)
    private Date createTime;
    @Field(type = FieldType.Date,format = DateFormat.basic_date_time)
    private Date updateTime;
}
