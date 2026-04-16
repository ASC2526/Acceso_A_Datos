package com.asc2526.da.unit5.library.repository;

import com.asc2526.da.unit5.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {
}