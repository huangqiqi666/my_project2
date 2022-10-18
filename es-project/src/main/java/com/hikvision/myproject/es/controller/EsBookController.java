package com.hikvision.myproject.es.controller;

import com.alibaba.fastjson.JSON;
import com.hikvision.myproject.es.AuthorEnum;
import com.hikvision.myproject.es.entity.es.ESBook;
import com.hikvision.myproject.es.repository.es.ESBookRepository;
import com.hikvision.myproject.es.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @Classname EsBookController
 * @Description TODO
 * @Date 2022/8/24 15:08
 * @Created by huangqiqi
 */
@Slf4j
@RestController(value = "esBook")
public class EsBookController {

    @Autowired
    BookService bookService;
    @Autowired
    ESBookRepository esBookRepository;
    @Autowired
    @Qualifier(value = "elasticsearchClient")
    RestHighLevelClient restHighLevelClient;
    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;


    //传书名，新增数据
    @GetMapping("/addBook")
    public void addBook() {
        List<String> titles = new ArrayList<>(Arrays.asList("葫芦娃与七个小个人", "卖导弹的小姑凉", "嘎子传奇", "日本岛沉没太平洋",
                "美利坚不美丽", "韩棒不棒","法兰西不浪漫","欧洲不缺气","澳大利亚很民主","印度三哥很卫生","越南猴子会上树","大俄毛熊顶住","中华民族必崛起"));
        for (String title : titles) {
            ESBook book = ESBook.builder()
//                .id(UUID.randomUUID().toString().substring(1,5))
                    .title(title).author(getRandomAuthor())
                    .price(new Random().nextDouble() * 90 + 10).createTime(new Date()).build();
            esBookRepository.save(book);
        }
    }

    public static void main(String[] args) {
        String randomAuthor=AuthorEnum.values()[new Random().nextInt(AuthorEnum.values().length)].toString();
        System.out.println(randomAuthor);
    }


    //返回随机作者
    private String getRandomAuthor(){
        return AuthorEnum.values()[new Random().nextInt(AuthorEnum.values().length)].toString();
    }


    @GetMapping("/findAll")
    public void findAll(){
        Page<ESBook> all = esBookRepository.findAll(PageRequest.of(0, 10));
        log.info(JSON.toJSONString(all));
    }

    @GetMapping("/deleteIndex")
    public void deleteIndex(){
        //删除索引
        elasticsearchRestTemplate.deleteIndex("book");
    }

    @GetMapping("/search")
    public List<ESBook> search(String keyword){
        return bookService.searchBook(keyword);
    }

    @GetMapping("/search1")
    public SearchHits<ESBook> search1(String keyword){
        return bookService.searchBook1(keyword);
    }

    @GetMapping(value = "/deleteById")
    public void deleteById(String id){
        esBookRepository.deleteById(id);
    }


    @PostMapping("/update")
    public void update(@RequestBody ESBook esBook){
        ESBook book = esBookRepository.findById(esBook.getId()).orElse(null);
        ESBook newBook = new ESBook();
        if (StringUtils.isEmpty(book.getAuthor())) {
            newBook.setAuthor(book.getAuthor());
        }
        if (StringUtils.isEmpty(book.getTitle())) {
            newBook.setTitle(book.getTitle());
        }

        if (StringUtils.isEmpty(book.getPrice())) {
            newBook.setPrice(book.getPrice());
        }

        ESBook book1 = esBookRepository.save(newBook);
    }



}
