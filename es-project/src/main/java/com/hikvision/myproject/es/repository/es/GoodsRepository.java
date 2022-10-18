package com.hikvision.myproject.es.repository.es;

import com.hikvision.myproject.es.entity.es.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
 
/**
 * 泛型一： 操作的实体类对象
 * 泛型二： 实体类的ID类型
 * @Author Niki_Ya
 * @Date 2022/4/2 16:38
 */
public interface GoodsRepository extends ElasticsearchRepository<Goods,Long> {
}