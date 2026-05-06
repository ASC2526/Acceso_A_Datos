package com.asc2526.da.unit5.library.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(name = "users")
public class User {

    @Id
    @NotBlank
    @Size(max = 8)
    @Column(name = "code", nullable = false, length = 8)
    private String code;

    @NotBlank
    @Size(max = 25)
    @Column(name = "name", nullable = false, length = 25)
    private String name;

    @NotBlank
    @Size(max = 25)
    @Column(name = "surname", nullable = false, length = 25)
    private String surname;

    @Past
    @Column(name = "birthdate")
    private LocalDate birthdate;

    @Column(name = "fined")
    private LocalDate fined;

    @Size(max = 9)
    @Column(name = "phone", length = 9)
    private String phone;

    @Email
    @Size(max = 100)
    @Column(name = "email", length = 100)
    private String email;

    public User() {}

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public LocalDate getBirthdate() { return birthdate; }
    public void setBirthdate(LocalDate birthdate) { this.birthdate = birthdate; }

    public LocalDate getFined() { return fined; }
    public void setFined(LocalDate fined) { this.fined = fined; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}