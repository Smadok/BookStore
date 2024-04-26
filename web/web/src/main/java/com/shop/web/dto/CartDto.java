package com.shop.web.dto;

import com.shop.web.models.Book;
import com.shop.web.models.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private int id;
    private UserDto userDto; //userDto
    private List<BookDto> books;//list bookDto
}
