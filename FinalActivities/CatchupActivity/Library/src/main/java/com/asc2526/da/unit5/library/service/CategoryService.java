package com.asc2526.da.unit5.library.service;

import com.asc2526.da.unit5.library.exception.CategoryNotFoundException;
import com.asc2526.da.unit5.library.model.Category;
import com.asc2526.da.unit5.library.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryByCode(String code) {
        return categoryRepository.findById(code)
                .orElseThrow(() -> new CategoryNotFoundException(code));
    }

    public Category createCategory(Category category) {
        if (category.getCode() == null || category.getCode().isBlank())
            throw new IllegalArgumentException("Category code is required");
        return categoryRepository.save(category);
    }

    public void deleteCategory(String code) {
        Category category = categoryRepository.findById(code)
                .orElseThrow(() -> new CategoryNotFoundException(code));

        categoryRepository.delete(category);
    }
}