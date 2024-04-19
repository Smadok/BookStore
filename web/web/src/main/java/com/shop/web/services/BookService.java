package com.shop.web.services;

import com.shop.web.dto.BookDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface BookService {
    void createBook(int bookShopID,BookDto bookDto);

    List<BookDto> findAllBooks();

    BookDto findByBookId(int bookId);

    void updateBook(BookDto bookDto);

    void delete(int bookId);
}
