package com.shop.web.controllers;

import com.shop.web.dto.BookDto;
import com.shop.web.models.Book;
import com.shop.web.services.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired

    public BookController(BookService bookService)
    {
        this.bookService = bookService;
    }
    @GetMapping("/books/{bookShopId}/new")
    public String createBookForm(@PathVariable("bookShopId") int bookShopId, Model model)
    {
        Book book = new Book();

        model.addAttribute("bookShopId",bookShopId);
        model.addAttribute("book",book);
        return "books-create";
    }
    @PostMapping("/books/{bookShopId}")
    public String createBook(@PathVariable("bookShopId") int bookShopId,
                             @ModelAttribute("book") BookDto bookDto,
                             Model model)
    {
        bookService.createBook(bookShopId,bookDto);
        return "redirect:/bookShop/" + bookShopId;
    }
    @GetMapping("/books")
    public String bookList(Model model)
    {
        List<BookDto> books = bookService.findAllBooks();
        model.addAttribute("books",books);
        return "books-list";
    }
    @GetMapping("/books/{bookId}")
    public String viewBook(@PathVariable("bookId") int bookId, Model model)
    {
        BookDto bookDto = bookService.findByBookId(bookId);
        model.addAttribute("book",bookDto);
        return "books-detail";
    }
    @GetMapping("/books/{bookId}/edit")
    public String editBook(@PathVariable("bookId") int bookId, Model model)
    {
        BookDto book =bookService.findByBookId(bookId);
        model.addAttribute("book",book);
        return "books-edit";
    }
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
        book.setBookShop(bookDto.getBookShop());
        bookService.updateBook(book);
        return "redirect:/books";
    }
    @GetMapping("/books/{bookId}/delete")
    public String bookDelete(@PathVariable("bookId") int bookId)
    {
        bookService.delete(bookId);
        return "redirect:/books";
    }
}