package com.shop.web.repository;

import com.shop.web.models.Book;
import com.shop.web.models.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;



@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class BookRepositoryTests {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testSaveBook() {
        Category category = Category.builder()
                .name("Fiction")
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

        Book savedBook = bookRepository.save(book);
        Book retrievedBook = bookRepository.findById(savedBook.getId()).orElse(null);

        assertThat(retrievedBook).isNotNull();
        assertThat(retrievedBook.getTitle()).isEqualTo("Harry Potter");
        assertThat(retrievedBook.getCategory()).isNotNull();
        assertThat(retrievedBook.getCategory().getName()).isEqualTo("Fiction");
    }

    @Test
    void testFindAllBooks() {
        Category category = Category.builder().name("Sci-fi").build();
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

        Book book2 = Book.builder()
                .title("Crime and Punishment")
                .author("Fyodor Dostoevsky")
                .price(20)
                .photoUrl("https://example.com/book.jpg")
                .createdOn(null)
                .updatedOn(null)
                .quantityAvailable(6)
                .category(savedCategory)
                .build();

        bookRepository.save(book);
        bookRepository.save(book2);

        List<Book> books = bookRepository.findAll();
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(2);
    }
    @Test
    public void testSearchBooks() {
        Category category = Category.builder()
                .name("Fiction")
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

        Book savedBook = bookRepository.save(book);
        List<Book> books = bookRepository.searchBooks("Harry");
        assertThat(books).isNotNull();
        assertThat(books.get(0).getTitle()).isEqualTo("Harry Potter");
    }
    @Test
    public void testDeleteBooks() {
        Category category = Category.builder()
                .name("Fiction")
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

        bookRepository.save(book);
        bookRepository.deleteById(book.getId());
        Optional<Book> bookReturn = bookRepository.findById(book.getId());

        assertThat(bookReturn).isEmpty();

    }
}
