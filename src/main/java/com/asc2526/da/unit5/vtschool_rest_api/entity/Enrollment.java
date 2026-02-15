package com.asc2526.da.unit5.vtschool_rest_api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "enrollments", schema = "_da_vtschool_2526")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code", nullable = false)
    private Integer id;

    @Column(name = "student", nullable = false, length = 12)
    private String studentId;

    @Column(name = "course", nullable = false)
    private Integer courseId;

    @Column(name = "year", nullable = false)
    private Integer year;

    public Enrollment() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
