package com.hikvision.myproject.es.repository;

import com.hikvision.myproject.es.entity.data.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 关系型数据库mysql Repository
 *
 * @author cloudgyb
 * @since 2022/3/19 19:31
 */
@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    List<Book> findByTitleAndPrice(String name, Integer price);
}
