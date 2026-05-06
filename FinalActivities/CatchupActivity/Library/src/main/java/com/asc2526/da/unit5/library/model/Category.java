package com.asc2526.da.unit5.library.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @NotBlank(message = "The code is required")
    @Size(max = 8)
    @Column(name = "code", nullable = false, length = 8)
    private String code;

    @NotBlank(message = "The name is required")
    @Size(max = 60)
    @Column(name = "name", nullable = false, length = 60)
    private String name;

    public Category() {}

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}