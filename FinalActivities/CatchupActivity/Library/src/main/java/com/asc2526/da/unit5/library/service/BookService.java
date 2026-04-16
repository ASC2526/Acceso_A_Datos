package com.asc2526.da.unit5.library.service;

import com.asc2526.da.unit5.library.exception.BookNotFoundException;
import com.asc2526.da.unit5.library.model.Book;
import com.asc2526.da.unit5.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAll() { return bookRepository.findAll(); }

    public Book findById(String isbn) {
        if (isbn == null) {
            throw new IllegalArgumentException("ISBN is required");
        }

        return bookRepository.findById(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));
    }
}
