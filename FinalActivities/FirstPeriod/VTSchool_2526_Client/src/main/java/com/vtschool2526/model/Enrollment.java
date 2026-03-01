package com.vtschool2526.model;

public class Enrollment {

    private Integer id;
    private String studentId;
    private Integer courseId;
    private Integer year;

    public Enrollment() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public Integer getCourseId() { return courseId; }
    public void setCourseId(Integer courseId) { this.courseId = courseId; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
}