package com.shop.web.controllers;

import com.shop.web.models.Cart;
import com.shop.web.services.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;


@Controller
public class CartController {
    private final CartService cartService;


    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    @GetMapping("/cart")
    public String viewCart(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        Cart cart = cartService.getCartByUserName(currentUserName);
        model.addAttribute("cart", cart);
        return "cart";
    }
    @PostMapping("/cart/removeBook")
    public String removeBookFromCart(@RequestParam("bookId") int bookId, HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        Cart cart = cartService.getCartByUserName(currentUserName);
        if (cart != null) {
            cartService.removeBookFromCart(cart.getId(), bookId);
        }

        return "redirect:/cart";
    }




}
