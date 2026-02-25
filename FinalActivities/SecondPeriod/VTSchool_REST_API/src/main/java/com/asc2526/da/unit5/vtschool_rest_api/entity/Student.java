package com.asc2526.da.unit5.vtschool_rest_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;


@Entity
@Table(name = "students", schema = "_da_vtschool_2526")
public class Student {
    @Id
    @Column(name = "idcard", nullable = false, length = 8)
    @NotBlank(message = "IdCard is required")
    @Size(min = 8, max = 8, message = "IdCard must have 8 characters")
    private String idcard;

    @Column(name = "firstname", nullable = false, length = 50)
    @NotBlank(message = "Firstname is required")
    private String firstname;

    @Column(name = "lastname", nullable = false, length = 100)
    @NotBlank(message = "Lastname is required")
    private String lastname;

    @Column(name = "phone", length = 12)
    @Pattern(regexp = "^\\d{0,12}$", message = "Phone must contain only digits, and 9 numbers at most")
    private String phone;

    @Column(name = "email", length = 100)
    @Email(message = "Invalid email format")
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