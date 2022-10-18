package com.hikvision.myproject.es.entity.es;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.elasticsearch.geometry.Point;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.data.elasticsearch.core.geo.GeoJsonPoint;

import java.util.Date;
/**
 * @author geng
 * 2020/12/18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(indexName = "book",createIndex = true,shards = 3,replicas = 1)
public class ESBook {
    @Id
    @Field(type = FieldType.Text)
    private String id;
    @Field(type = FieldType.Text,analyzer="ik_max_word")
    private String title;
    @Field(type = FieldType.Keyword,analyzer="ik_max_word")
    private String author;
    @Field(type = FieldType.Double)
    private Double price;
    @CreatedDate
    @Field(type = FieldType.Date,format = DateFormat.basic_date_time)
    private Date createTime;
    @LastModifiedDate
    @Field(type = FieldType.Date,format = DateFormat.basic_date_time)
    private Date updateTime;

    //es提供的经纬度
    @GeoPointField
    Point location;
    GeoJsonPoint location2;
}
