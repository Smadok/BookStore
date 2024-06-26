package com.shop.web.controllers;

import com.shop.web.dto.BookDto;
import com.shop.web.dto.CartDto;
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
        String currentUserName = getCurrentUserName();

        CartDto cart = cartService.getCartByUserName(currentUserName);
        double calculatedTotalPrice = cartService.calculateTotalPrice(cart);
        model.addAttribute("calculatedTotalPrice", calculatedTotalPrice);
        model.addAttribute("cart", cart);
        return "cart";
    }
    @PostMapping("/cart/removeBook")
    public String removeBookFromCart(@RequestParam("bookId") int bookId, HttpSession session) {
        String currentUserName = getCurrentUserName();

        CartDto cart = cartService.getCartByUserName(currentUserName);
        if (cart != null) {
            cartService.removeBookFromCart(cart.getId(), bookId);
        }

        return "redirect:/cart";
    }
    @GetMapping("/cart/checkout")
    public String checkout(Model model, HttpSession session) {

        String currentUserName = getCurrentUserName();
        CartDto currentCart = cartService.getCartByUserName(currentUserName);
        double calculatedTotalPrice = cartService.calculateTotalPrice(currentCart);

        session.setAttribute("calculatedTotalPrice", calculatedTotalPrice);

        return "redirect:/orders/new";
    }
    private String getCurrentUserName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }


}
