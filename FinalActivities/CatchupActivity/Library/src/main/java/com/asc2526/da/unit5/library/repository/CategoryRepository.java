package com.asc2526.da.unit5.library.repository;

import com.asc2526.da.unit5.library.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
}