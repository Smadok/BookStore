package com.shop.web.controllers;

import com.shop.web.dto.BookDto;
import com.shop.web.dto.UserDto;
import com.shop.web.models.Book;
import com.shop.web.models.UserEntity;
import com.shop.web.services.BookService;
import com.shop.web.services.CartService;
import com.shop.web.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class BookController
{
    private BookService bookService;
    private UserService userService;
    private CartService cartService;
    @Autowired

    public BookController(BookService bookService,UserService userService,CartService cartService) {
        this.bookService = bookService;
        this.userService=userService;
        this.cartService=cartService;
    }
    @GetMapping("/books/{categoryId}/new")
    public String createBookForm(@PathVariable("categoryId") int categoryId, Model model)
    {
        Book book = new Book();
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("book",book);
        return "books-create";
    }
    @PostMapping("/books/{categoryId}")
    public String createBook(@PathVariable("categoryId") int categoryId,
                             @ModelAttribute("book") BookDto bookDto,
                             Model model)
    {
        bookService.createBook(categoryId,bookDto);
        return "redirect:/category/" + categoryId;
    }
    @GetMapping("/books")
    @PreAuthorize("@securityConfig.admin or @securityConfig.user")
    public String bookList(Model model)
    {
        List<BookDto> books = bookService.findAllBooks();
        model.addAttribute("books",books);
        model.addAttribute("isAdmin",isAdminUser());
        return "books-list";
    }
    @PreAuthorize("@securityConfig.admin or @securityConfig.user")
    @GetMapping("/books/{bookId}")
    public String viewBook(@PathVariable("bookId") int bookId, Model model)
    {
        BookDto bookDto = bookService.findByBookId(bookId);
        model.addAttribute("book",bookDto);
        model.addAttribute("isAdmin",isAdminUser());
        return "books-detail";
    }

    @GetMapping("/books/{bookId}/edit")
    public String editBook(@PathVariable("bookId") int bookId, Model model)
    {
        BookDto book =bookService.findByBookId(bookId);
        model.addAttribute("book",book);
        return "books-edit";
    }
    @PreAuthorize("@securityConfig.admin")
    @PostMapping("books/{bookId}/edit")
    public  String updateBook(@PathVariable("bookId") int bookId
            , @Valid @ModelAttribute("book")BookDto book,
                              BindingResult result,
                              Model model)
    {
        if(result.hasErrors()){
            model.addAttribute("book",book);
            return "books-edit";
        }
        BookDto bookDto=bookService.findByBookId(bookId);
        book.setId(bookId);
        book.setCategory(bookDto.getCategory());
        bookService.updateBook(book);
        return "redirect:/books";
    }
    @PreAuthorize("@securityConfig.admin")
    @GetMapping("/books/{bookId}/delete")
    public String bookDelete(@PathVariable("bookId") int bookId) {
        bookService.delete(bookId);
        return "redirect:/books";
    }

    @PostMapping("/books/{bookId}/addToCart")
    public String addToCart(@PathVariable("bookId") int bookId) {
        String currentUserName = getCurrentUserName();

        UserEntity currentUser = userService.findByUsername(currentUserName);
        BookDto book = bookService.findByBookId(bookId);
        cartService.addBookToCart(currentUser.getCart().getId(), book.getId());
        return "redirect:/books" ;
    }
    private String getCurrentUserName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
    private boolean isAdminUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ADMIN"));
    }
}
