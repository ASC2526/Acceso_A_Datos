package com.asc2526.da.unit5.vtschool_rest_api.service;

import com.asc2526.da.unit5.vtschool_rest_api.entity.Enrollment;
import com.asc2526.da.unit5.vtschool_rest_api.entity.Score;
import com.asc2526.da.unit5.vtschool_rest_api.entity.Subject;
import com.asc2526.da.unit5.vtschool_rest_api.exception.CourseNotFoundException;
import com.asc2526.da.unit5.vtschool_rest_api.exception.EnrollmentNotFoundException;
import com.asc2526.da.unit5.vtschool_rest_api.exception.StudentNotFoundException;
import com.asc2526.da.unit5.vtschool_rest_api.repository.*;
import com.asc2526.da.unit5.vtschool_rest_api.web.dto.ScoreCourseYearDTO;
import com.asc2526.da.unit5.vtschool_rest_api.web.dto.ScoreDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScoreService {

    private final ScoreRepository scoreRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final SubjectRepository subjectRepository;

    public ScoreService(
            ScoreRepository scoreRepository,
            EnrollmentRepository enrollmentRepository,
            StudentRepository studentRepository,
            CourseRepository courseRepository,
            SubjectRepository subjectRepository
    ) {
        this.scoreRepository = scoreRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.subjectRepository = subjectRepository;
    }


    public List<Score> findScores(
            Integer enrollmentId,
            String studentId,
            Integer courseId,
            Boolean passed,
            Boolean nullScores
    ) {

        List<Score> result;

        if (enrollmentId != null) {

            if (!enrollmentRepository.existsById(enrollmentId)) {
                throw new EnrollmentNotFoundException(enrollmentId);
            }

            result = scoreRepository.findByEnrollmentId(enrollmentId);
        }
        else if (studentId != null && courseId != null) {

            if (!studentRepository.existsById(studentId)) {
                throw new StudentNotFoundException(studentId);
            }

            if (!courseRepository.existsById(courseId)) {
                throw new CourseNotFoundException(courseId);
            }

            Enrollment enrollment = enrollmentRepository
                    .findByStudentIdAndCourseId(studentId, courseId)
                    .orElseThrow(() ->
                            new EnrollmentNotFoundException(courseId)
                    );

            result = scoreRepository.findByEnrollmentId(enrollment.getId());
        }
        else {
            result = scoreRepository.findAll();
        }

        if (passed != null) {

            List<Score> filtered = new ArrayList<>();

            for (Score s : result) {

                if (passed && s.getScore() != null && s.getScore() >= 5) {
                    filtered.add(s);
                }

                if (!passed && s.getScore() != null && s.getScore() < 5) {
                    filtered.add(s);
                }
            }

            return filtered;
        }

        if (nullScores != null && nullScores) {

            List<Score> filtered = new ArrayList<>();

            for (Score s : result) {
                if (s.getScore() == null) {
                    filtered.add(s);
                }
            }

            return filtered;
        }

        return result;
    }


    public List<ScoreDTO> findPendingScores(String studentId, Integer courseId) {

        List<Score> scores =
                scoreRepository.findPendingByStudentAndCourse(studentId, courseId);

        List<ScoreDTO> result = new ArrayList<>();

        for (Score score : scores) {

            Enrollment enrollment = enrollmentRepository
                    .findById(score.getEnrollmentId())
                    .orElseThrow();

            Subject subject = subjectRepository.findById(score.getSubjectId())
                    .orElseThrow();

            result.add(new ScoreDTO(
                    score.getId(),
                    subject.getName(),
                    subject.getYear(),
                    enrollment.getYear(),
                    score.getScore()
            ));
        }

        return result;
    }

    @Transactional
    public List<Score> saveAll(List<Score> scores) {

        if (scores == null || scores.isEmpty()) {
            throw new IllegalArgumentException("Score list cannot be empty");
        }

        List<Score> result = new ArrayList<>();

        for (Score score : scores) {

            if (score.getEnrollmentId() == null ||
                    score.getSubjectId() == null ||
                    score.getScore() == null) {
                throw new IllegalArgumentException("Invalid score data");
            }

            if (score.getScore() < 0 || score.getScore() > 10) {
                throw new IllegalArgumentException("Score must be between 0 and 10");
            }

            if (!enrollmentRepository.existsById(score.getEnrollmentId())) {
                throw new EnrollmentNotFoundException(score.getEnrollmentId());
            }

            Optional<Score> existing =
                    scoreRepository.findByEnrollmentIdAndSubjectId(
                            score.getEnrollmentId(),
                            score.getSubjectId()
                    );

            if (existing.isPresent()) {
                Score stored = existing.get();
                stored.setScore(score.getScore());
                result.add(scoreRepository.save(stored));
            } else {
                result.add(scoreRepository.save(score));
            }
        }

        return result;
    }

    public List<ScoreDTO> getScoresForStudent(
            String studentId,
            Integer courseId
    ) {
        return scoreRepository.findScoresForStudent(studentId, courseId);
    }

    public List<ScoreCourseYearDTO> getScoresForCourseYear(
            Integer courseId,
            Integer year
    ) {

        courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));

        int currentYear = Year.now().getValue();
        if(year > currentYear || year < 2023) {
            throw new IllegalArgumentException("Invalid academic year");
        }
        return scoreRepository.findScoresForCourseYear(courseId, year);
    }


    @Transactional
    public void updateScore(Integer scoreId, Integer value) {

        if (value < 0 || value > 10) {
            throw new IllegalArgumentException("Score must be between 0 and 10");
        }

        Score score = scoreRepository.findById(scoreId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Score not found")
                );

        score.setScore(value);

        scoreRepository.save(score);
    }

}