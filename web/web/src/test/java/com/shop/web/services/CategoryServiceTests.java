package com.shop.web.services;

import com.shop.web.dto.CategoryDto;
import com.shop.web.models.Category;
import com.shop.web.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTests {
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryImpl categoryService;

    private Category category1;
    private Category category2;
    private CategoryDto categoryDto;
    @BeforeEach
    public void init()
    {
        category1= Category.builder()
                .name("Fiction")
                .books(new ArrayList<>())
                .build();
        category2 = Category.builder()
                .name("History")
                .books(new ArrayList<>()).build();
        categoryDto = CategoryDto.builder()
                .name("Fiction")
                .books(new ArrayList<>())
                .build();
    }
    @Test
    public void testFindAllCategories()
    {

        List<Category> categories = Arrays.asList(category1, category2);
        //act
        when(categoryRepository.findAll()).thenReturn(categories);
        List<CategoryDto> foundCategories = categoryService.findAllCategories();

        assertThat(foundCategories).isNotNull();
        assertThat(foundCategories.size()).isEqualTo(2);
    }
   @Test
   public void testSaveCategory()
   {
       when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(category1);
       Category savedCategory = categoryService.saveCategory(categoryDto);
       assertThat(savedCategory).isNotNull();
       assertThat(savedCategory.getName()).isEqualTo("Fiction");
   }
    @Test
    public void findCategoriesById()
    {
        when(categoryRepository.findById(category1.getId())).thenReturn(Optional.ofNullable(category1));
        CategoryDto foundCategoryById = categoryService.findCategoriesById(categoryDto.getId());
        assertThat(foundCategoryById).isNotNull();
        assertThat(foundCategoryById.getName()).isEqualTo("Fiction");
    }
    @Test
    public void testUpdateCategory() {
        when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(category1);
        categoryService.updateCategory(categoryDto);
        assertAll(() -> categoryService.updateCategory(categoryDto));
    }
    @Test
    public void testDeleteCategory() {
        int categoryId=1;
        categoryService.delete(categoryId);
        assertAll(() -> categoryService.delete(categoryId));
    }
    @Test
    public void testSearchCategories() {
        // Arrange
        List<Category> categories = Arrays.asList(category1);
        when(categoryRepository.searchCategories("Fiction")).thenReturn(categories);

        // Act
        List<CategoryDto> searchedCategories = categoryService.searchCategories("Fiction");

        // Assert
        assertThat(searchedCategories).isNotNull();
        assertThat(searchedCategories.size()).isEqualTo(1);
        assertThat(searchedCategories.get(0).getName()).isEqualTo("Fiction");
    }
}
