package com.vtschool2526.model;

public class Subject {

    private Integer id;
    private String name;
    private Integer year;
    private Integer hours;

    public Subject() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public Integer getHours() { return hours; }
    public void setHours(Integer hours) { this.hours = hours; }
}