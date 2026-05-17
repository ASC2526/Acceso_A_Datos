package com.asc2526.da.unit5.library.service;

import com.asc2526.da.unit5.library.dto.BookRequestDTO;
import com.asc2526.da.unit5.library.exception.BookAlreadyExistsException;
import com.asc2526.da.unit5.library.exception.BookNotFoundException;
import com.asc2526.da.unit5.library.exception.CategoryNotFoundException;
import com.asc2526.da.unit5.library.model.Book;
import com.asc2526.da.unit5.library.model.Category;
import com.asc2526.da.unit5.library.repository.BookRepository;
import com.asc2526.da.unit5.library.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;


    public BookService(BookRepository bookRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Book> getAll() {
        return bookRepository.findAll().stream()
                .filter(book -> book.getCopies() != null && book.getCopies() > 0)
                .collect(Collectors.toList());
    }

    public Book findById(String isbn) {
        if (isbn == null) {
            throw new IllegalArgumentException("ISBN is required");
        }

        return bookRepository.findById(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));
    }

    public List<Book> findByCategory(String categoryCode) {
        return bookRepository.findByCategoryCode(categoryCode);
    }

    public List<Book> getAvailableBooks() {

        return bookRepository.findByCopiesGreaterThan(0);
    }

    public List<Book> getBooksByCategory(String categoryCode) {

        if (categoryCode == null || categoryCode.isBlank()) {
            throw new IllegalArgumentException("Category cannot be null or empty");
        }

        Category category = categoryRepository.findById(categoryCode)
                .orElseThrow(() -> new CategoryNotFoundException(categoryCode));

        return bookRepository.findByCategory(category);
    }

    @Transactional
    public Book createBook(BookRequestDTO dto) {

        if (bookRepository.existsById(dto.getIsbn())) {
            throw new BookAlreadyExistsException(dto.getIsbn());
        }

        Category category = categoryRepository.findById(dto.getCategoryCode())
                .orElseThrow(() -> new CategoryNotFoundException(dto.getCategoryCode()));

        Book book = new Book();
        book.setIsbn(dto.getIsbn());
        book.setTitle(dto.getTitle());
        book.setCopies(dto.getCopies());
        book.setCategory(category);

        return bookRepository.save(book);
    }
}
