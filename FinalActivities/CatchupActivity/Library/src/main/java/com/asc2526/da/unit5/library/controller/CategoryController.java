package com.asc2526.da.unit5.library.controller;

import com.asc2526.da.unit5.library.model.Category;
import com.asc2526.da.unit5.library.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{code}")
    public Category getCategoryByCode(@PathVariable String code) {
        return categoryService.getCategoryByCode(code);
    }

    @PostMapping
    public Category createCategory(@Valid @RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    @DeleteMapping("/{code}")
    public void deleteCategory(@PathVariable String code) {
        categoryService.deleteCategory(code);
    }
}