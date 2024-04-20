package com.shop.web.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.util.List;


import java.time.LocalDateTime;
@Data
@Builder

public class CategoryDto
{
    private int id;
    @NotEmpty(message = "should not be empty")
    private String name;
    private String content;
    private LocalDateTime createdOn;
    private List<BookDto> books;
}
