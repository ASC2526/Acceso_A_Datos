package com.vtschool2526.model;

public class Student {

    private String idcard;
    private String firstname;
    private String lastname;
    private String phone;
    private String email;
    private String address;

    public Student() {}

    public String getIdcard() { return idcard; }
    public void setIdcard(String idcard) { this.idcard = idcard; }

    public String getFirstname() { return firstname; }
    public void setFirstname(String firstname) { this.firstname = firstname; }

    public String getLastname() { return lastname; }
    public void setLastname(String lastname) { this.lastname = lastname; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

}