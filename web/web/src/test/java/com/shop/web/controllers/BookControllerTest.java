package com.shop.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.web.dto.BookDto;

import com.shop.web.services.BookService;
import com.shop.web.services.CartService;
import com.shop.web.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private UserService userService;
    @MockBean
    private CartService cartService;

    @Autowired
    private ObjectMapper objectMapper;

    private BookDto bookDto;
    private BookDto bookDto2;

    @BeforeEach
    public void init() {
        bookDto = BookDto.builder()
                .title("Harry Potter")
                .author("J.K. Rowling")
                .price(15.99)
                .photoUrl("https://example.com/book.jpg")
                .quantityAvailable(1)
                .build();
        bookDto2= BookDto.builder()
                .title("The Hobbit")
                .author("J.R.R. Tolkien")
                .price(20.99)
                .photoUrl("https://example.com/book2.jpg")
                .quantityAvailable(2)
                .build();
    }
    @Test
    void testCreateBookForm() throws Exception {
        int categoryId = 1;

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/books/{categoryId}/new", categoryId)
                .with(user("username").password("password").roles("USER")));

        response.andExpect(status().isOk())
                .andExpect(view().name("books-create"))
                .andExpect(model().attributeExists("categoryId"))
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attribute("categoryId", categoryId));
    }
    @Test
    void testCreateBook() throws Exception {
        int categoryId = 1;


        given(bookService.createBook(ArgumentMatchers.anyInt(), ArgumentMatchers.any(BookDto.class)))
                .willAnswer(invocation -> invocation.getArgument(1));


        ResultActions response = mockMvc.perform(post("/books/{categoryId}", categoryId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("title", bookDto.getTitle())
                .param("author", bookDto.getAuthor())
                .param("price", String.valueOf(bookDto.getPrice()))
                .param("photoUrl", bookDto.getPhotoUrl())
                .param("quantityAvailable", String.valueOf(bookDto.getQuantityAvailable()))
                .with(user("username").password("password").roles("USER")));


        response.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/category/" + categoryId))
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    void testBookListAsAdmin() throws Exception {
        List<BookDto> bookList = Arrays.asList(bookDto, bookDto2);
        given(bookService.findAllBooks()).willReturn(bookList);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/books")
                .with(csrf())
                .with(user("ADMIN").password("password").roles("ADMIN")));

        response.andExpect(status().isOk())
                .andExpect(view().name("books-list"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attribute("books", bookList))
                .andExpect(model().attribute("isAdmin", true))
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    void testViewBook() throws Exception {

        int bookId=1;
        given(bookService.findByBookId(ArgumentMatchers.anyInt())).willReturn(bookDto);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/books/{bookId}",bookId)
                .with(csrf())
                .param("title", bookDto.getTitle())
                .with(user("ADMIN").password("password").roles("ADMIN")));

        response.andExpect(status().isOk())
                .andExpect(view().name("books-detail"))
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attribute("isAdmin", true))
                .andExpect(model().attribute("book", hasProperty("title", is("Harry Potter"))))
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    void testEditBook() throws Exception
    {
        int bookId=1;
        given(bookService.findByBookId(anyInt())).willReturn(bookDto);
        ResultActions response = mockMvc.perform(get("/books/{bookId}/edit",bookId)
                .with(csrf())
                .param("title", bookDto.getTitle())
                .with(user("username").password("password").roles("User")));

        response.andExpect(status().isOk());
    }
    @Test
    void testUpdateBook() throws Exception
    {
        int bookId=1;
        given(bookService.findByBookId(anyInt())).willReturn(bookDto);
        doAnswer(invocation -> {
            BookDto bookArg = invocation.getArgument(0);
            bookDto.setTitle(bookArg.getTitle());
            bookDto.setAuthor(bookArg.getAuthor());
            return null;
        }).when(bookService).updateBook(any(BookDto.class));


        ResultActions response = mockMvc.perform(post("/books/{bookId}/edit",bookId)
                .with(csrf())
                .param("title", bookDto2.getTitle())
                .param("author", bookDto2.getAuthor())
                .with(user("User").password("password").roles("USER")));

        response.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"))
                .andDo(MockMvcResultHandlers.print());


        verify(bookService).updateBook(any(BookDto.class));

        assert bookDto.getTitle().equals(bookDto2.getTitle());
        assert bookDto.getAuthor().equals(bookDto2.getAuthor());
        assert bookDto.getTitle().equals("The Hobbit");

    }

}
