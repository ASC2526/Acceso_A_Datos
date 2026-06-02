package com.asc2526.da.unit5.library.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Entity
@JsonIgnoreProperties
@Table(name = "users")
public class User {

    @Id
    @NotBlank
    @Size(max = 8)
    @Pattern(regexp = "^[A-Z0-9]{7,8}$", message = "Invalid code format")
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
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fined;

    @Size(max = 9)
    @Pattern(regexp = "^\\d{9}$", message = "Phone must be exactly 9 digits")
    @Column(name = "phone", length = 9)
    private String phone;

    @Email(message = "Invalid email format")
    @Size(max = 100)
    @Column(name = "email", length = 100)
    private String email;

    @Transient
    public String getFullName() {
        return name + " " + surname;
    }

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