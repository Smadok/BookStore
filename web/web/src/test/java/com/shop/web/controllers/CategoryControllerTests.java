package com.shop.web.controllers;

import com.shop.web.dto.CategoryDto;
import com.shop.web.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CategoryController.class)
public class CategoryControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CategoryService categoryService;
    private CategoryDto categoryDto;
    @BeforeEach
    public void init()
    {
        categoryDto = CategoryDto.builder()
                .name("Fiction")
                .books(new ArrayList<>())
                .build();
    }
    @Test
    void testSaveCategoryForm() throws Exception{
        given(categoryService.saveCategory(ArgumentMatchers.any(CategoryDto.class)))
                .willAnswer(invocation -> invocation.getArgument(1));
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/category/new")
                .with(csrf())
                .with(user("username").password("password").roles("USER"))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", categoryDto.getName()));

        response.andExpect(status().isOk())
                .andExpect(view().name("category-create"))
                .andDo(MockMvcResultHandlers.print());

    }
}
