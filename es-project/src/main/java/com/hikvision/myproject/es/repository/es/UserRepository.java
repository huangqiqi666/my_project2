package com.hikvision.myproject.es.repository.es;

import com.hikvision.myproject.es.entity.es.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @Classname UserRepository
 * @Description TODO
 * @Date 2022/8/25 16:29
 * @Created by huangqiqi
 */
@Repository
public interface UserRepository extends ElasticsearchRepository<User,String> {


    SearchHits<User>findByNameLikeAndAgeAfter(String name,Integer age);

    Page<User>findAll(Pageable pageable);



}
