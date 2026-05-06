package com.asc2526.da.unit5.library.controller;

import com.asc2526.da.unit5.library.model.Category;
import com.asc2526.da.unit5.library.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{code}")
    public ResponseEntity<Category> getCategoryByCode(@PathVariable String code) {
        return ResponseEntity.ok(categoryService.getCategoryByCode(code));
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category) {
        Category newCategory = categoryService.createCategory(category);
        return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String code) {
        categoryService.deleteCategory(code);
        return ResponseEntity.noContent().build();
    }
}