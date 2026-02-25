package com.asc2526.da.unit5.vtschool_rest_api.web.dto;

public class ScoreDTO {

    private Integer scoreId;
    private String subjectName;
    private Integer subjectYear;
    private Integer academicYear;
    private Integer score;

    public ScoreDTO(Integer scoreId,
                    String subjectName,
                    Integer subjectYear,
                    Integer academicYear,
                    Integer score) {
        this.scoreId = scoreId;
        this.subjectName = subjectName;
        this.subjectYear = subjectYear;
        this.academicYear = academicYear;
        this.score = score;
    }

    public Integer getScoreId() { return scoreId; }
    public String getSubjectName() { return subjectName; }
    public Integer getSubjectYear() { return subjectYear; }
    public Integer getAcademicYear() { return academicYear; }
    public Integer getScore() { return score; }

    public void setScore(Integer score) {
        this.score = score;
    }
}