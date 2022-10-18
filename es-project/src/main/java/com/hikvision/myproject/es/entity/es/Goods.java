package com.hikvision.myproject.es.entity.es;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.Map;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(indexName = "goods",shards = 1)
public class Goods {

    @Id
    @Field(type = FieldType.Long,store = true)
    private Long id;            // 主键Id
    @Field(type = FieldType.Text,analyzer = "ik_smart",store = true)
    private String name;        // 商品名称
    @Field(type = FieldType.Integer,store = true)
    private Integer price;      // 商品价格
    @Field(type = FieldType.Text,store = true,index = false)
    private String image;       // 商品图片src
    @Field(type = FieldType.Date,store = true,index = false)
    private Date createTime;    // 商品创建时间
    @Field(type = FieldType.Long,store = true,index = false)
    private Long spuId;         // Spu的Id
    @Field(type = FieldType.Keyword,store = true)
    private String categoryName;// 分类名称
    @Field(type = FieldType.Keyword,store = true)
    private String brandName;   // 品牌名称
    @Field(type = FieldType.Object,store = true)
    private Map spec;           // 规格Map Map<String,String>，如<"颜色","黑色">
    @Field(type = FieldType.Integer,store = true,index = false)
    private Integer saleNum;    // 销量

}