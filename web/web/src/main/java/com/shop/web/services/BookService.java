package com.shop.web.services;

import com.shop.web.dto.BookDto;
import com.shop.web.models.Book;

import java.util.List;


public interface BookService {
    void createBook(int categoryID, BookDto bookDto);

    List<BookDto> findAllBooks();

    BookDto findByBookId(int bookId);

    void updateBook(BookDto bookDto);

    void delete(int bookId);


}
