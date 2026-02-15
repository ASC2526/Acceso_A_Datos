package com.asc2526.da.unit5.vtschool_rest_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;


@Entity
@Table(name = "students", schema = "_da_vtschool_2526")
public class Student {
    @Id
    @Pattern(regexp = "\\d{8}", message = "Idcard must have exactly 8 digits")
    @Column(name = "idcard", nullable = false, length = 8)
    private String idcard;

    @NotBlank
    @Column(name = "firstname", nullable = false, length = 50)
    private String firstname;

    @NotBlank
    @Column(name = "lastname", nullable = false, length = 100)
    private String lastname;

    @Pattern(regexp = "\\d{9}", message = "Phone must have 9 digits")
    @Column(name = "phone", length = 12)
    private String phone;

    @Email(message = "Invalid email format")
    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "address", length = 256)
    private String address;


    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Student() {}
}