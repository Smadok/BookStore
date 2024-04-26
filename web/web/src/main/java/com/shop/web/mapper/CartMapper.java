package com.shop.web.mapper;

import com.shop.web.dto.CartDto;
import com.shop.web.dto.UserDto;
import com.shop.web.models.Cart;
import com.shop.web.models.UserEntity;

import java.util.stream.Collectors;

import static com.shop.web.mapper.BookMapper.mapToBook;
import static com.shop.web.mapper.BookMapper.mapToBookDto;
import static com.shop.web.mapper.UserMapper.mapToUserDto;
import static com.shop.web.mapper.UserMapper.mapToUser;

public class CartMapper {
    public static CartDto mapToCartDto(Cart cart) {
        UserDto userDto = mapToUserDto(cart.getUser());

        return CartDto.builder()
                .id(cart.getId())
                .userDto(userDto)
                .books(cart.getBooks().stream().map((book) -> mapToBookDto(book)).collect(Collectors.toList()))
                .build();
    }
    public static Cart mapToCart(CartDto cartDto) {
        UserEntity userEntity = mapToUser(cartDto.getUserDto());
        return Cart.builder()
                .id(cartDto.getId())
                .user(userEntity)
                .books(cartDto.getBooks().stream().map((bookDto) -> mapToBook(bookDto)).collect(Collectors.toList()))
                .build();
    }


}
