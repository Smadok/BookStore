package com.shop.web.controllers;

import com.shop.web.dto.CategoryDto;
import com.shop.web.models.Category;
import com.shop.web.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller

public class CategoryController {
    CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @PreAuthorize("@securityConfig.admin or @securityConfig.user")
    @GetMapping("/category")
    public String listStores(Model model)
    {
        List<CategoryDto> categories = categoryService.findAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("isAdmin",isAdminUser());
        return "category-list";
    }
    @PreAuthorize("@securityConfig.admin or @securityConfig.user")
    @GetMapping("/category/{categoryId}")
    public String categoryDetail(@PathVariable("categoryId") int categoryId, Model model)
    {
        CategoryDto categoryDto = categoryService.findCategoriesById(categoryId);
        model.addAttribute("category", categoryDto);
        model.addAttribute("isAdmin",isAdminUser());
        return "category-detail";
    }

    @PreAuthorize("@securityConfig.admin")
    @GetMapping("/category/new")
    public String createCategoryForm(Model model)
    {
        Category category = new Category();
        model.addAttribute("category", category);
        return "category-create";
    }
    @PostMapping("/category/new")
    public String saveCategoryForm(@Valid @ModelAttribute("category") CategoryDto categoryDto,
                                   BindingResult result,
                                   Model model)
    {
        if (result.hasErrors())
        {
            model.addAttribute("category", categoryDto);
            return "category-create";
        }

        categoryService.saveCategory(categoryDto);
        return "redirect:/category";
    }
    @PreAuthorize("@securityConfig.admin")
    @GetMapping("/category/{categoryId}/edit")
    public String editCategory(@PathVariable("categoryId") int categoryId, Model model)
    {
        CategoryDto category = categoryService.findCategoriesById(categoryId);
        model.addAttribute("category", category);
        return "categoryEdit";
    }
    @PostMapping("category/{categoryId}/edit")
    public  String updateCategory(@PathVariable("categoryId") int categoryId
            , @Valid @ModelAttribute("category") CategoryDto category,
                                  BindingResult result, Model model)
    {
        if(result.hasErrors()){
            model.addAttribute("category", category);
            return "categoryEdit";
        }
        category.setId(categoryId);
        categoryService.updateCategory(category);
        return "redirect:/category";
    }
    @PreAuthorize("@securityConfig.admin")
    @GetMapping("/category/{categoryId}/delete")
    public String categoryDelete(@PathVariable("categoryId") int categoryId)
    {
        categoryService.delete(categoryId);
        return "redirect:/category";
    }
    @GetMapping("/category/search")
    public String searchCategory(@RequestParam(value = "query") String query, Model model)
    {
        List<CategoryDto> categoryDtos = categoryService.searchCategories(query);
        model.addAttribute("categories", categoryDtos);
        return "category-list";
    }
    private boolean isAdminUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ADMIN"));
    }
}
