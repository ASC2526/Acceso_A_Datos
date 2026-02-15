package com.asc2526.da.unit5.vtschool_rest_api.service;

import com.asc2526.da.unit5.vtschool_rest_api.entity.Enrollment;
import com.asc2526.da.unit5.vtschool_rest_api.entity.Score;
import com.asc2526.da.unit5.vtschool_rest_api.exception.CourseNotFoundException;
import com.asc2526.da.unit5.vtschool_rest_api.exception.EnrollmentNotFoundException;
import com.asc2526.da.unit5.vtschool_rest_api.exception.StudentNotFoundException;
import com.asc2526.da.unit5.vtschool_rest_api.repository.CourseRepository;
import com.asc2526.da.unit5.vtschool_rest_api.repository.EnrollmentRepository;
import com.asc2526.da.unit5.vtschool_rest_api.repository.ScoreRepository;
import com.asc2526.da.unit5.vtschool_rest_api.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScoreService {

    private final ScoreRepository scoreRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public ScoreService(
            ScoreRepository scoreRepository,
            EnrollmentRepository enrollmentRepository,
            StudentRepository studentRepository,
            CourseRepository courseRepository
    ) {
        this.scoreRepository = scoreRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }


    public List<Score> findScores(
            Integer enrollmentId,
            String studentId,
            Integer courseId,
            Boolean passed
    ) {

        List<Score> result;

        // por enrollmentId
        if (enrollmentId != null) {

            if (!enrollmentRepository.existsById(enrollmentId)) {
                throw new EnrollmentNotFoundException(enrollmentId);
            }

            result = scoreRepository.findByEnrollmentId(enrollmentId);
        }
        // por student y course
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
        // sin filtros
        else {
            result = scoreRepository.findAll();
        }

        // asignaturas aprobadas
        if (passed != null) {

            List<Score> filtered = new ArrayList<>();

            for (Score s : result) {

                if (passed && s.getScore() >= 5) {
                    filtered.add(s);
                }

                if (!passed && s.getScore() < 5) {
                    filtered.add(s);
                }
            }

            return filtered;
        }

        return result;
    }

    public Score save(Score score) {

        if (score == null ||
                score.getEnrollmentId() == null ||
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
            return scoreRepository.save(stored);
        }

        return scoreRepository.save(score);
    }
}
