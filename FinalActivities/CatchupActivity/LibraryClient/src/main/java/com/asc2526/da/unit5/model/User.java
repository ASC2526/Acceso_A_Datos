package com.asc2526.da.unit5.model;

import java.time.LocalDate;

public class User {

    private String code;
    private String name;
    private String surname;
    private String phone;
    private String email;
    private LocalDate fined;

    public User() {}

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDate getFined() { return fined; }
    public void setFined(LocalDate fined) { this.fined = fined; }
}