package com.shop.web.mapper;

import com.shop.web.dto.CartDto;
import com.shop.web.models.Book;
import com.shop.web.models.Cart;

import java.util.stream.Collectors;

public class CartMapper {
    public CartDto mapToCartDto(Cart cart) {
        return CartDto.builder()
                .id(cart.getId())
                .user(cart.getUser())
                .books(cart.getBooks())
                .build();
    }
    public Cart mapToCart(CartDto cartDto) {
        return Cart.builder()
                .id(cartDto.getId())
                .user(cartDto.getUser())
                .books(cartDto.getBooks())
                .build();
    }


}
