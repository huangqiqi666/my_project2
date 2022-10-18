package com.hikvision.myproject.es.controller;

import com.hikvision.myproject.es.entity.data.Book;
import com.hikvision.myproject.es.entity.es.ESBook;
import com.hikvision.myproject.es.service.BookService;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author geng
 * 2020/12/20
 */
@RestController
public class BookController {
    private final BookService bookService;
    
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/book")
    public Map<String,String> addBook(@RequestBody Book book){
        bookService.addBook(book);
        Map<String,String> map = new HashMap<>();
        map.put("msg","ok");
        return map;
    }

    @GetMapping("/book/search")
    public SearchHits<ESBook> search(String key){
        return bookService.searchBook1(key);
    }
}
