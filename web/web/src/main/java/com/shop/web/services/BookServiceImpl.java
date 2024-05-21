package com.shop.web.services;

import com.shop.web.dto.BookDto;
import com.shop.web.models.Book;
import com.shop.web.models.Category;
import com.shop.web.repository.BookRepository;
import com.shop.web.repository.CategoryRepository;
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
    private CategoryRepository categoryRepository;
    @Autowired
    public BookServiceImpl(BookRepository bookRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public BookDto createBook(int categoryID, BookDto bookDto) {
        Category category = categoryRepository.findById(categoryID).get();
        Book book = mapToBook(bookDto);
        book.setCategory(category);
        bookRepository.save(book);
        return bookDto;
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
    @Override
    public void decreaseBookQuantity(int bookId, int quantity) {
        Book book = bookRepository.findById(bookId).get();

        int currentQuantity = book.getQuantityAvailable();
        if (currentQuantity < quantity) {
            throw new IllegalArgumentException("Not enough quantity available for book: " + book.getTitle());
        }

        int newQuantity = currentQuantity - quantity;
        book.setQuantityAvailable(newQuantity);
        bookRepository.save(book);
    }
    @Override
    public List<BookDto> searchBooks(String query) {
        List<Book> books = bookRepository.searchBooks(query);
        return books.stream().map(book -> mapToBookDto(book)).collect(Collectors.toList());

    }

}
