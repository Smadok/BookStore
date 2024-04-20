package com.shop.web.services;
import com.shop.web.dto.CategoryDto;
import com.shop.web.models.Category;
import java.util.List;

public interface CategoryService {
    List<CategoryDto> findAllCategories();
    Category saveCategory(CategoryDto categoryDto);
    CategoryDto findCategoriesById(int categoryId);
    void updateCategory(CategoryDto category);
    void delete(int categoryId);
    List<CategoryDto> searchCategories(String query);
}
