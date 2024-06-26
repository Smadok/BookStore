package com.shop.web.services;

import com.shop.web.dto.BookDto;
import java.util.List;


public interface BookService {
    BookDto createBook(int categoryID, BookDto bookDto);

    List<BookDto> findAllBooks();

    BookDto findByBookId(int bookId);

    void updateBook(BookDto bookDto);

    void delete(int bookId);
    void decreaseBookQuantity(int bookId, int quantity);
    List<BookDto> searchBooks(String query);


}
