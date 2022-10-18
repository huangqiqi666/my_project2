package com.hikvision.myproject.es.controller;

import com.alibaba.fastjson.JSON;
import com.hikvision.myproject.es.entity.es.User;
import com.hikvision.myproject.es.repository.es.UserRepository;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname EsUserController
 * @Description TODO
 * @Date 2022/8/25 16:30
 * @Created by huangqiqi
 */
@RestController(value = "/esUser")
public class EsUserController {

    @Autowired
    private UserRepository userRepository;
    //注入 ElasticsearchRestTemplate
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @GetMapping("/add")
    public void add(){
        List<User> users = new ArrayList<>();
        users.add(User.builder().id("1").age(10).name("张三").build());
        users.add(User.builder().id("2").age(15).name("李四").build());
        users.add(User.builder().id("3").age(20).name("王五").build());
        users.add(User.builder().id("4").age(80).name("徐六").build());
        userRepository.saveAll(users);
    }

    @GetMapping("/queryById")
    public void queryById(String id){
        User user = userRepository.findById(id).orElse(null);
        System.out.println(user);
    }

    @GetMapping("/queryByNameAndAge")
    public void queryByNameAndAge(String name,Integer age){
        SearchHits<User> hits = userRepository.findByNameLikeAndAgeAfter(name, age);
        System.out.println(JSON.toJSONString(hits));
    }

    //分页
    @GetMapping("findAllPage")
    public void queryAllPage(){
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.DESC, "id");
        Page<User> pages = userRepository.findAll(pageable);
        System.out.println(JSON.toJSONString(pages));
    }

    @GetMapping(value = "/delete")
    public void deleteById(String id){
        userRepository.deleteById(id);
//        userRepository.delete(User.builder().id(id).build());
    }

    @GetMapping("/termQuery")
    public void termQuery(){
        TermQueryBuilder termQuery = QueryBuilders.termQuery("name", "张三");
        Page<User> page = userRepository.search(termQuery, PageRequest.of(0, 10));
        System.out.println(JSON.toJSONString(page));
    }


    @GetMapping("/criteriaQuery")
    public void criteriaQuery(String name,Integer age){
        Criteria criteria = new Criteria("name").is(name).and(new Criteria("age").greaterThanEqual(age).or("id").is("3"));
        Query query=new CriteriaQuery(criteria);
        Page<User> search = userRepository.search(query);
        System.out.println(JSON.toJSONString(search));

    }

    @GetMapping("/stringQuery")
    public void stringQuery(){
        Query query=new StringQuery("{ \\\"match\\\": { \\\"name\\\": { \\\"query\\\": \\\"Jack\\\" } } } ");
        Page<User> search = userRepository.search(query);
        System.out.println(JSON.toJSONString(search));
    }

    @GetMapping("/nativeSearchQuery")
    public void nativeSearchQuery(){

    }




}
