package com.asc2526.da.unit5.service;

import com.asc2526.da.unit5.api.ApiManager;
import com.asc2526.da.unit5.model.Book;
import com.asc2526.da.unit5.util.BookHandler;
import com.asc2526.da.unit5.util.XMLParser;

import javax.xml.parsers.SAXParser;
import java.io.File;
import java.util.List;

public class BookService {

    private static final ApiManager api = new ApiManager();

    public static void importBooks(String filename) {

        // 1. check file exists
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("ERROR: File not found: " + filename);
            return;
        }

        // 2. parse XML
        List<Book> books;
        try {
            SAXParser parser = XMLParser.createParser();
            BookHandler handler = new BookHandler();
            parser.parse(file, handler);
            books = handler.getBooks();
        } catch (Exception e) {
            System.out.println("ERROR: Could not parse XML file: " + e.getMessage());
            return;
        }

        if (books.isEmpty()) {
            System.out.println("ERROR: No books found in the XML file.");
            return;
        }

        // 3. validate books
        for (Book book : books) {

            if (book.getIsbn() == null || book.getIsbn().isBlank()) {
                System.out.println("ERROR: ISBN is mandatory. Import aborted.");
                return;
            }

            if (book.getTitle() == null || book.getTitle().isBlank()) {
                System.out.println("ERROR: Title is mandatory"
                        + " (ISBN: " + book.getIsbn() + "). Import aborted.");
                return;
            }

            if (book.getCopies() == null || book.getCopies() <= 0) {
                System.out.println("ERROR: Copies must be greater than 0"
                        + " (ISBN: " + book.getIsbn() + "). Import aborted.");
                return;
            }
        }

        // 4. send books
        int imported = 0;
        for (Book book : books) {
            try {
                api.addBook(book);
                imported++;
            } catch (Exception e) {
                System.out.println("ERROR: " + book.getIsbn()
                        + ": " + e.getMessage() + ". Import aborted.");
                return;
            }
        }

        System.out.println("Books imported successfully: " + imported + " book(s).");
    }
}