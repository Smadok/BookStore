package com.shop.web.controllers;

import com.shop.web.dto.BookShopDto;
import com.shop.web.models.BookShop;
import com.shop.web.services.BookShopService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BookShopController {
    BookShopService bookShopService;

    public BookShopController(BookShopService bookShopService) {
        this.bookShopService = bookShopService;
    }
    @GetMapping("/bookShop")
    public String listStores(Model model)
    {
        List<BookShopDto> bookShops = bookShopService.findAllBookShops();
        model.addAttribute("bookShops",bookShops);
        return "listbookstores";
    }
    @GetMapping("/bookShop/{bookShopId}")
    public String bookShopDetail(@PathVariable("bookShopId") int bookShopId,Model model)
    {
        BookShopDto bookShopDto = bookShopService.findBookShopsById(bookShopId);
        model.addAttribute("bookShop",bookShopDto);
        return "bookShop-detail";
    }


    @GetMapping("/bookShop/new")
    public String createBookStoreForm(Model model)
    {
        BookShop bookShop = new BookShop();
        model.addAttribute("bookShop",bookShop);
        return "bookshop-create";
    }
    @PostMapping("/bookShop/new")
    public String saveBookStoreForm(@Valid @ModelAttribute("bookShop")BookShopDto bookShopDto,
                                    BindingResult result,
                                    Model model)
    {
        if (result.hasErrors())
        {
            model.addAttribute("bookShop",bookShopDto);
            return "bookshop-create";
        }

        bookShopService.saveBookShop(bookShopDto);
        return "redirect:/bookShop";
    }
    @GetMapping("/bookShop/{bookShopId}/edit")
    public String editBookShop(@PathVariable("bookShopId") int bookShopId, Model model)
    {
        BookShopDto bookShop =bookShopService.findBookShopsById(bookShopId);
        model.addAttribute("bookShop",bookShop);
        return "bookShopEdit";
    }
    @PostMapping("bookShop/{bookShopId}/edit")
    public  String updateBookShop(@PathVariable("bookShopId") int bookShopId
            , @Valid @ModelAttribute("bookShop")BookShopDto bookShop,
                                  BindingResult result, Model model)
    {
        if(result.hasErrors()){
            model.addAttribute("bookShop",bookShop);
            return "bookShopEdit";
        }
        bookShop.setId(bookShopId);
        bookShopService.updateBookShop(bookShop);
        return "redirect:/bookShop";
    }
    @GetMapping("/bookShop/{bookShopId}/delete")
    public String bookShopDelete(@PathVariable("bookShopId") int bookShopId)
    {
        bookShopService.delete(bookShopId);
        return "redirect:/bookShop";
    }
    @GetMapping("/bookShop/search")
    public String searchBookShop(@RequestParam(value = "query") String query,Model model)
    {
        List<BookShopDto> bookShopDtos = bookShopService.searchBookShops(query);
        model.addAttribute("bookShops",bookShopDtos);
        return "listbookstores";
    }
}
