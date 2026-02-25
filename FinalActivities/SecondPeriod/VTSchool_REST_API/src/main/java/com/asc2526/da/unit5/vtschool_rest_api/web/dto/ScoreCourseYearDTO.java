package com.asc2526.da.unit5.vtschool_rest_api.web.dto;

public class ScoreCourseYearDTO {
    private Integer year;
    private Integer score;
    private String studentId;
    private Integer courseYear;
    private String courseName;

    public ScoreCourseYearDTO(Integer year, Integer score, String studentId, Integer courseYear, String courseName) {
        this.year = year;
        this.score = score;
        this.studentId = studentId;
        this.courseYear = courseYear;
        this.courseName = courseName;
    }

    public Integer getYear() {
        return year;
    }

    public Integer getScore() {
        return score;
    }

    public String getStudentId() {
        return studentId;
    }

    public Integer getCourseYear() {
        return courseYear;
    }

    public String getCourseName() {
        return courseName;
    }
}
