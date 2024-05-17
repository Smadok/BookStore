package com.shop.web.repository;

import com.shop.web.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {
    @Query("SELECT b FROM Book b WHERE b.title LIKE concat('%', :query,'%') ")
    List<Book> searchBooks(String query);
}
