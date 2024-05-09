package com.shop.web.mapper;

import com.shop.web.dto.CategoryDto;
import com.shop.web.models.Category;

import java.util.stream.Collectors;

import static com.shop.web.mapper.BookMapper.mapToBookDto;


public class CategoryMapper {
    public static Category mapToCategory(CategoryDto category)
    {
        Category categoryDto = Category.builder()
                .id(category.getId())
                .name(category.getName())
                .photoUrl(category.getPhotoUrl())
                .build();
        return categoryDto;
    }

    public static CategoryDto mapToCategoryDto(Category category) {
        CategoryDto categoryDto = CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .photoUrl(category.getPhotoUrl())
                .books(category.getBooks().stream().map((book) -> mapToBookDto(book)).collect(Collectors.toList()))
                .build();
        return categoryDto;
    }
}
