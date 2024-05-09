package com.shop.web.mapper;

import com.shop.web.dto.BookDto;
import com.shop.web.models.Book;

public class BookMapper {
    public static Book mapToBook(BookDto bookDto)
    {
        return Book.builder()
                .id(bookDto.getId())
                .title(bookDto.getTitle())
                .author(bookDto.getAuthor())
                .price(bookDto.getPrice())
                .photoUrl(bookDto.getPhotoUrl())
                .createdOn(bookDto.getCreatedOn())
                .updatedOn(bookDto.getUpdatedOn())
                .category(bookDto.getCategory())
                .quantityAvailable(bookDto.getQuantityAvailable())
                .build();
    }
    public static BookDto mapToBookDto(Book book)
    {
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .price(book.getPrice())
                .photoUrl(book.getPhotoUrl())
                .createdOn(book.getCreatedOn())
                .updatedOn(book.getUpdatedOn())
                .category(book.getCategory())
                .quantityAvailable(book.getQuantityAvailable())
                .build();
    }
}
