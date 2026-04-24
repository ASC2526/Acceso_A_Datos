package com.asc2526.da.unit5.library.repository;

import com.asc2526.da.unit5.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {
    List<Book> findByCategory(String category);
}