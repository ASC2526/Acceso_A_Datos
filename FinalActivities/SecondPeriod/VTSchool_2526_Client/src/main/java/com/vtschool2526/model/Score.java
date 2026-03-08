package com.vtschool2526.model;

public class Score {

    private Integer id;
    private Integer enrollmentId;
    private Integer subjectId;
    private Integer score;

    public Score() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getEnrollmentId() { return enrollmentId; }
    public void setEnrollmentId(Integer enrollmentId) { this.enrollmentId = enrollmentId; }

    public Integer getSubjectId() { return subjectId; }
    public void setSubjectId(Integer subjectId) { this.subjectId = subjectId; }

    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
}