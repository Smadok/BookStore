package com.shop.web.services;

import com.shop.web.dto.BookDto;
import com.shop.web.models.Book;
import com.shop.web.models.BookShop;
import com.shop.web.repository.BookRepository;
import com.shop.web.repository.BookShopRepository;
import com.shop.web.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.shop.web.mapper.BookMapper.mapToBook;
import static com.shop.web.mapper.BookMapper.mapToBookDto;

@Service
public class BookServiceImpl implements BookService
{
    private BookRepository bookRepository;
    private BookShopRepository bookShopRepository;
    @Autowired

    public BookServiceImpl(BookRepository bookRepository, BookShopRepository bookShopRepository) {
        this.bookRepository = bookRepository;
        this.bookShopRepository = bookShopRepository;
    }

    @Override
    public void createBook(int bookShopID, BookDto bookDto) {
        BookShop bookShop = bookShopRepository.findById(bookShopID).get();
        Book book = mapToBook(bookDto);
        book.setBookShop(bookShop);
        bookRepository.save(book);
    }

    @Override
    public List<BookDto> findAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(book -> mapToBookDto(book)).collect(Collectors.toList());
    }

    @Override
    public BookDto findByBookId(int bookId) {
        Book book = bookRepository.findById(bookId).get();
        return mapToBookDto(book);
    }

    @Override
    public void updateBook(BookDto bookDto)
    {
        Book book =mapToBook(bookDto);
        bookRepository.save(book);
    }

    @Override
    public void delete(int bookId) {
        bookRepository.deleteById(bookId);
    }


}
