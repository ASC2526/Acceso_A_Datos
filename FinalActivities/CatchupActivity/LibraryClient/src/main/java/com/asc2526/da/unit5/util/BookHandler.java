package com.asc2526.da.unit5.util;

import com.asc2526.da.unit5.model.Book;
import com.asc2526.da.unit5.model.Category;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class BookHandler extends DefaultHandler {

    private final List<Book> books = new ArrayList<>();
    private Book current = null;
    private String tagContent = null;

    public List<Book> getBooks() {
        return books;
    }

    @Override
    public void startElement(String uri, String localName,
                             String qName, Attributes attributes) {
        if (qName.equalsIgnoreCase("book")) {
            current = new Book();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        tagContent = new String(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) {

        if (current == null) return;

        if (qName.equalsIgnoreCase("isbn")) {
            current.setIsbn(tagContent.trim());
        }
        else if (qName.equalsIgnoreCase("title")) {
            current.setTitle(tagContent.trim());
        }
        else if (qName.equalsIgnoreCase("copies")) {
            current.setCopies(Integer.parseInt(tagContent.trim()));
        }
        else if (qName.equalsIgnoreCase("outline")) {
            current.setOutline(tagContent.trim());
        }
        else if (qName.equalsIgnoreCase("publisher")) {
            current.setPublisher(tagContent.trim());
        }
        else if (qName.equalsIgnoreCase("category")) {
            Category cat = new Category();
            cat.setCode(tagContent.trim());
            current.setCategory(cat);
        }
        else if (qName.equalsIgnoreCase("book")) {
            books.add(current);
            current = null;
        }
    }
}