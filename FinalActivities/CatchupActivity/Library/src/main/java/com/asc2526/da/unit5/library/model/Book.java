package com.asc2526.da.unit5.library.model;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @Column(name = "isbn", nullable = false, length = 13)
    private String isbn;

    @Column(name = "title", nullable = false, length = 90)
    private String title;

    @ColumnDefault("1")
    @Column(name = "copies")
    private Integer copies;

    @Column(name = "outline")
    private String outline;

    @Column(name = "publisher", length = 60)
    private String publisher;

    @Column(name = "category", nullable = false)
    private String category;

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

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}