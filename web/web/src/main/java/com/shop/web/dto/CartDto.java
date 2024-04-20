package com.shop.web.dto;

import com.shop.web.models.Book;
import com.shop.web.models.UserEntity;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class CartDto {
    private int id;
    private UserEntity user;
    private List<Book> books;
}
