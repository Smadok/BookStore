package com.shop.web.mapper;

import com.shop.web.dto.BookShopDto;
import com.shop.web.models.BookShop;

import java.util.stream.Collectors;

import static com.shop.web.mapper.BookMapper.mapToBookDto;


public class BookShopMapper {
    public static BookShop mapToBookShop(BookShopDto bookShop)
    {
        BookShop bookShopDto = BookShop.builder()
                .id(bookShop.getId())
                .name(bookShop.getName())
                .build();
        return bookShopDto;
    }

    public static BookShopDto mapToBookShopDto(BookShop bookShop) {
        BookShopDto bookShopDto = BookShopDto.builder()
                .id(bookShop.getId())
                .name(bookShop.getName())
                .books(bookShop.getBooks().stream().map((book) -> mapToBookDto(book)).collect(Collectors.toList()))
                .build();
        return bookShopDto;
    }
}
