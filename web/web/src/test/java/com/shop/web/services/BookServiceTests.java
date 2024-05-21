package com.shop.web.services;

import com.shop.web.dto.BookDto;
import com.shop.web.models.Book;
import com.shop.web.models.Category;
import com.shop.web.repository.BookRepository;
import com.shop.web.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.shop.web.mapper.BookMapper.mapToBookDto;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTests {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private BookServiceImpl bookService;
    @Test
    public void testCreateBook()
    {
        Category category = Category.builder()
                .name("Fiction")
                .id(1)
                .build();
        Category savedCategory = categoryRepository.save(category);

        Book book = Book.builder()
                .title("Harry Potter")
                .author("J.K. Rowling")
                .price(15.99)
                .photoUrl("https://example.com/book.jpg")
                .createdOn(null)
                .updatedOn(null)
                .quantityAvailable(1)
                .category(savedCategory)
                .build();
        BookDto bookDto = mapToBookDto(book);

        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);
        BookDto savedBook = bookService.createBook(category.getId(), bookDto);

        assertThat(savedBook).isNotNull();
        assertThat(savedBook.getTitle()).isEqualTo("Harry Potter");

    }
    @Test
    public void testFindAllBooks()
    {
        Category category = Category.builder()
                .name("Fiction")
                .id(1)
                .build();
        Category savedCategory = categoryRepository.save(category);

        Book book1 = Book.builder()
                .title("Harry Potter")
                .author("J.K. Rowling")
                .price(15.99)
                .photoUrl("https://example.com/book.jpg")
                .createdOn(null)
                .updatedOn(null)
                .quantityAvailable(1)
                .category(savedCategory)
                .build();
        Book book2 = Book.builder()
                .title("The Hobbit")
                .author("J.R.R. Tolkien")
                .price(20.99)
                .photoUrl("https://example.com/book2.jpg")
                .createdOn(null)
                .updatedOn(null)
                .quantityAvailable(1)
                .category(category)
                .build();

        List<Book> books = Arrays.asList(book1, book2);
        //act
        when(bookRepository.findAll()).thenReturn(books);
        List<BookDto> foundBooks = bookService.findAllBooks();


        assertThat(foundBooks).isNotNull();
        assertThat(foundBooks.size()).isEqualTo(2);

        List<BookDto> expectedBooks = books.stream().map(book -> mapToBookDto(book)).collect(Collectors.toList());
        assertThat(foundBooks).isEqualTo(expectedBooks);

    }
    @Test
    public void testFindByBookId()
    {
        Category category = Category.builder()
                .name("Fiction")
                .id(1)
                .build();
        Category savedCategory = categoryRepository.save(category);

        Book book = Book.builder()
                .title("Harry Potter")
                .author("J.K. Rowling")
                .price(15.99)
                .photoUrl("https://example.com/book.jpg")
                .createdOn(null)
                .updatedOn(null)
                .quantityAvailable(1)
                .category(savedCategory)
                .build();
        BookDto bookDto = mapToBookDto(book);

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        BookDto returnedBook = bookService.findByBookId(book.getId());
        assertThat(returnedBook).isNotNull();
        assertThat(returnedBook.getTitle()).isEqualTo("Harry Potter");
    }
    @Test
    public void testUpdateBook()
    {
        Category category = Category.builder()
                .name("Fiction")
                .id(1)
                .build();
        Category savedCategory = categoryRepository.save(category);

        Book book = Book.builder()
                .title("Harry Potter")
                .author("J.K. Rowling")
                .price(15.99)
                .photoUrl("https://example.com/book.jpg")
                .createdOn(null)
                .updatedOn(null)
                .quantityAvailable(1)
                .category(savedCategory)
                .build();
        BookDto bookDto = mapToBookDto(book);

        when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);

        bookService.updateBook(bookDto);

        assertAll(()->bookService.updateBook(bookDto));
    }
    @Test
    public void testDeleteBook()
    {
        int bookId =1;

        bookService.delete(bookId);

        assertAll(()->bookService.delete(bookId));
    }
}
