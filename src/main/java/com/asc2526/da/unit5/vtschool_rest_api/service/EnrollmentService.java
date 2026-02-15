package com.asc2526.da.unit5.vtschool_rest_api.service;

import com.asc2526.da.unit5.vtschool_rest_api.entity.Enrollment;
import com.asc2526.da.unit5.vtschool_rest_api.exception.*;
import com.asc2526.da.unit5.vtschool_rest_api.repository.CourseRepository;
import com.asc2526.da.unit5.vtschool_rest_api.repository.EnrollmentRepository;
import com.asc2526.da.unit5.vtschool_rest_api.repository.ScoreRepository;
import com.asc2526.da.unit5.vtschool_rest_api.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final ScoreRepository scoreRepository;

    public EnrollmentService(
            EnrollmentRepository enrollmentRepository,
            StudentRepository studentRepository,
            CourseRepository courseRepository,
            ScoreRepository scoreRepository
    ) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.scoreRepository = scoreRepository;
    }

    public Enrollment create(Enrollment enrollment) {

        if (enrollment == null ||
                enrollment.getStudentId() == null ||
                enrollment.getCourseId() == null ||
                enrollment.getYear() == null) {
            throw new IllegalArgumentException("Invalid enrollment data");
        }

        String studentId = enrollment.getStudentId();
        Integer courseId = enrollment.getCourseId();
        Integer year = enrollment.getYear();

        validateYear(year);

        if (!studentRepository.existsById(studentId)) {
            throw new StudentNotFoundException(studentId);
        }

        if (!courseRepository.existsById(courseId)) {
            throw new CourseNotFoundException(courseId);
        }

        if (enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId)) {
            throw new EnrollmentAlreadyExistsException(
                    "Student already enrolled in this course"
            );
        }

        List<Enrollment> sameYear =
                enrollmentRepository.findByStudentIdAndYear(studentId, year);

        if (!sameYear.isEmpty()) {
            throw new EnrollmentAlreadyExistsException(
                    "Student already enrolled in another course in same year"
            );
        }

        return enrollmentRepository.save(enrollment);
    }



    public List<Enrollment> findEnrollments(
            String studentId,
            Integer courseId,
            Integer year
    ) {

        if (studentId != null && !studentRepository.existsById(studentId)) {
            throw new StudentNotFoundException(studentId);
        }

        if (courseId != null && !courseRepository.existsById(courseId)) {
            throw new CourseNotFoundException(courseId);
        }

        if (studentId != null && courseId != null) {
            Optional<Enrollment> opt =
                    enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId);

            List<Enrollment> result = new ArrayList<>();
            opt.ifPresent(result::add);
            return result;
        }

        if (studentId != null) {
            return enrollmentRepository.findByStudentId(studentId);
        }

        if (courseId != null) {
            return enrollmentRepository.findByCourseId(courseId);
        }

        if (year != null) {
            return enrollmentRepository.findByYear(year);
        }

        return enrollmentRepository.findAll();
    }

    public void deleteById(Integer id) {

        if (id == null) {
            throw new IllegalArgumentException("Enrollment id is required");
        }

        if (!enrollmentRepository.existsById(id)) {
            throw new EnrollmentNotFoundException(id);
        }

        if (scoreRepository.existsByEnrollmentId(id)) {
            throw new IllegalStateException(
                    "Enrollment has scores and cannot be deleted"
            );
        }

        enrollmentRepository.deleteById(id);
    }

    private void validateYear(Integer year) {

        int currentYear = java.time.Year.now().getValue();

        if (year <= 0) {
            throw new IllegalArgumentException("Year must be positive");
        }

        if (year < currentYear) {
            throw new IllegalArgumentException("Year cannot be before the current year");
        }

        if (year > currentYear + 1) {
            throw new IllegalArgumentException("Year is too far in the future");
        }
    }
}
