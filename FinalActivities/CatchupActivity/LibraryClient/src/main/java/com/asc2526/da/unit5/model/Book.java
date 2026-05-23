package com.asc2526.da.unit5.model;

public class Book {

    private String isbn;
    private String title;
    private Integer copies;
    private String outline;
    private String publisher;
    private Category category;

    public Book() {}

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Integer getCopies() { return copies; }
    public void setCopies(Integer copies) { this.copies = copies; }

    public String getOutline() { return outline; }
    public void setOutline(String outline) { this.outline = outline; }

    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
}