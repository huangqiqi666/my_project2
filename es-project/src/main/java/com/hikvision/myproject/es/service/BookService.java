package com.hikvision.myproject.es.service;

import com.hikvision.myproject.es.entity.data.Book;
import com.hikvision.myproject.es.entity.es.ESBook;
import com.hikvision.myproject.es.repository.BookRepository;
import com.hikvision.myproject.es.repository.es.ESBookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

/**
 * @author geng
 * 2020/12/19
 */
@Slf4j
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final ESBookRepository esBookRepository;
    private final TransactionTemplate transactionTemplate;

    public BookService(BookRepository bookRepository,
                       ESBookRepository esBookRepository,
                       TransactionTemplate transactionTemplate) {
        this.bookRepository = bookRepository;
        this.esBookRepository = esBookRepository;
        this.transactionTemplate = transactionTemplate;
    }

    /**
     * @Description 添加数据（新增到db、es）
     * @param book
     * @return void
     * @date   2022/8/24 15:43
     * @Author huangqiqi
     */
    public void addBook(Book book) {
        //db
        final Book saveBook = transactionTemplate.execute((status) ->
                bookRepository.save(book)
        );
        //es
        final ESBook esBook = new ESBook();
        assert saveBook != null;
        BeanUtils.copyProperties(saveBook, esBook);
        esBook.setId(saveBook.getId()+"");
        try {
            esBookRepository.save(esBook);
        }catch (Exception e){
            log.error(String.format("保存ES错误！%s", e.getMessage()));
        }
    }

    /**
     * @Description 查询返回List实体
     * @param keyword
     * @return java.util.List<com.hikvision.myproject.es.entity.es.ESBook>
     * @date   2022/8/24 15:43
     * @Author huangqiqi
     */
    public List<ESBook> searchBook(String keyword){
        return esBookRepository.findByTitleOrAuthor(keyword, keyword);
    }

    /**
     * @Description 查询返回原生es数据
     * @param keyword
     * @return org.springframework.data.elasticsearch.core.SearchHits<com.hikvision.myproject.es.entity.es.ESBook>
     * @date   2022/8/24 15:42
     * @Author huangqiqi
     */
    public SearchHits<ESBook> searchBook1(String keyword){
        return esBookRepository.find(keyword);
    }

}
