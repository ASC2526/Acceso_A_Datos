package com.asc2526.da.unit5.vtschool_rest_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "enrollments", schema = "_da_vtschool_2526")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code")
    private Integer id;

    @Column(name = "student", nullable = false)
    @NotBlank(message = "Student id is required")
    private String studentId;

    @Column(name = "course", nullable = false)
    @NotNull(message = "Course id is required")
    private Integer courseId;

    @Column(name = "year", nullable = false)
    @NotNull(message = "Year is required")
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
