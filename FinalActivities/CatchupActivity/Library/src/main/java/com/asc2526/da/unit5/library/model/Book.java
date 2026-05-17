package com.asc2526.da.unit5.library.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @NotBlank(message = "The ISBN is required")
    @Size(max = 13)
    @Column(name = "isbn", nullable = false, length = 13)
    private String isbn;

    @NotBlank(message = "The title is required")
    @Size(max = 90)
    @Column(name = "title", nullable = false, length = 90)
    private String title;

    @NotNull(message = "The number of copies cannot be null")
    @Min(value = 0, message = "The copies cannot be negative")
    @Column(name = "copies")
    private Integer copies;

    @Size(max = 255)
    @Column(name = "outline")
    private String outline;

    @Size(max = 60)
    @Column(name = "publisher", length = 60)
    private String publisher;

    @JsonIgnoreProperties({"books", "handler", "hibernateLazyInitializer"})
    @NotNull(message = "The category is required")
    @ManyToOne
    @JoinColumn(name = "category", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "book")
    @JsonIgnoreProperties("book")
    private java.util.List<Lending> lendings;

    @Transient
    public int getAvailableCopies() {
        if (lendings == null) return copies;
        long active = lendings.stream()
                .filter(l -> l.getReturningDate() == null)
                .count();
        return copies - (int) active;
    }

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
    public void setCategory(Category categoryObject) { this.category = categoryObject; }

}