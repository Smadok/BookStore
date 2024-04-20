package com.shop.web.services;

import com.shop.web.dto.CategoryDto;
import com.shop.web.models.Category;
import com.shop.web.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.shop.web.mapper.CategoryMapper.mapToCategory;
import static com.shop.web.mapper.CategoryMapper.mapToCategoryDto;

@Service
public class CategoryImpl implements CategoryService
{
    private CategoryRepository categoryRepository;
    @Autowired
    public CategoryImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    @Override
    public List<CategoryDto> findAllCategories()
    {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map((category) -> mapToCategoryDto(category)).collect(Collectors.toList());
    }
    @Override
    public Category saveCategory(CategoryDto categoryDto)
    {
        Category category = mapToCategory(categoryDto);
        return categoryRepository.save(category);
    }

    @Override
    public CategoryDto findCategoriesById(int categoryId)
    {
        Category category = categoryRepository.findById(categoryId).get();
        return mapToCategoryDto(category);
    }

    @Override
    public void updateCategory(CategoryDto categoryDto)
    {
        Category category = mapToCategory(categoryDto);
        categoryRepository.save(category);
    }

    @Override
    public void delete(int categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public List<CategoryDto> searchCategories(String query) {
        List<Category> categories = categoryRepository.searchCategories(query);
        return categories.stream().map(category -> mapToCategoryDto(category)).collect(Collectors.toList());

    }



}
