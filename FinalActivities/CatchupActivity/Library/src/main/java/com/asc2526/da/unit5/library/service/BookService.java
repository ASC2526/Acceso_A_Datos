package com.asc2526.da.unit5.library.service;

import com.asc2526.da.unit5.library.exception.BookAlreadyExistsException;
import com.asc2526.da.unit5.library.exception.BookNotFoundException;
import com.asc2526.da.unit5.library.exception.CategoryNotFoundException;
import com.asc2526.da.unit5.library.model.Book;
import com.asc2526.da.unit5.library.repository.BookRepository;
import com.asc2526.da.unit5.library.repository.CategoryRepository;
import com.asc2526.da.unit5.library.repository.LendingRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final LendingRepository lendingRepository;
    private final CategoryRepository categoryRepository;


    public BookService(BookRepository bookRepository, LendingRepository lendingRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.lendingRepository = lendingRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Book> getAll() { return bookRepository.findAll(); }

    public Book findById(String isbn) {
        if (isbn == null) {
            throw new IllegalArgumentException("ISBN is required");
        }

        return bookRepository.findById(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));
    }

    public List<Book> getAvailableBooks() {

        List<Book> books = bookRepository.findAll();
        List<Book> availableBooks = new ArrayList<>();

        for (Book book : books)
        {
            int activeBooks = lendingRepository.countActiveLendingsByBook(book.getIsbn());
            int copies = book.getCopies() != null ? book.getCopies() : 0;
            int available = copies - activeBooks;

            if (available > 0)
            {
                availableBooks.add(book);
            }
        }

        return availableBooks;
    }

    public List<Book> getBooksByCategory(String category) {

        if (category == null || category.isBlank()) {
            throw new IllegalArgumentException("Category cannot be null or empty");
        }

        categoryRepository.findById(category)
                .orElseThrow(() -> new CategoryNotFoundException(category));

        return bookRepository.findByCategory(category);
    }

    public Book createBook(Book book) {

        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }

        if (book.getIsbn() == null || book.getIsbn().isBlank()) {
            throw new IllegalArgumentException("ISBN cannot be null or empty");
        }

        if (book.getTitle() == null || book.getTitle().isBlank()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }

        if (book.getCategory() == null || book.getCategory().isBlank()) {
            throw new IllegalArgumentException("Category cannot be null or empty");
        }

        if (bookRepository.existsById(book.getIsbn())) {
            throw new BookAlreadyExistsException(book.getIsbn());
        }

        categoryRepository.findById(book.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException(book.getCategory()));

        return bookRepository.save(book);
    }
}
