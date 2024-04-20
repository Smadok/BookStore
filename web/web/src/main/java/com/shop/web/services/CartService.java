package com.shop.web.services;

import com.shop.web.dto.CartDto;
import com.shop.web.models.Cart;

import java.util.List;

public interface CartService {

    void deleteCart(int id);
    public void addBookToCart(int cartId, int bookId);
    Cart getCartByUserName(String currentUserName);
}