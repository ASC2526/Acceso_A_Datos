package com.asc2526.da.unit5.vtschool_rest_api.service;

import com.asc2526.da.unit5.vtschool_rest_api.entity.*;
import com.asc2526.da.unit5.vtschool_rest_api.exception.*;
import com.asc2526.da.unit5.vtschool_rest_api.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final ScoreRepository scoreRepository;
    private final SubjectRepository subjectRepository;

    public EnrollmentService(
            EnrollmentRepository enrollmentRepository,
            StudentRepository studentRepository,
            CourseRepository courseRepository,
            ScoreRepository scoreRepository,
            SubjectRepository subjectRepository
    ) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.scoreRepository = scoreRepository;
        this.subjectRepository = subjectRepository;
    }

    @Transactional
    public Enrollment create(Enrollment enrollment) {

        if (enrollment == null) {
            throw new IllegalArgumentException("Enrollment cannot be null");
        }

        String studentId = enrollment.getStudentId();
        Integer courseId = enrollment.getCourseId();
        Integer year = enrollment.getYear();

        studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));

        courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));

        int currentYear = Year.now().getValue();
        if (year < currentYear || year > currentYear + 1) {
            throw new IllegalArgumentException("Invalid academic year");
        }

        boolean existsThisYear =
                enrollmentRepository.existsByStudentIdAndYear(studentId, year);

        if (existsThisYear) {
            throw new EnrollmentAlreadyExistsException(
                    "Student already enrolled this year");
        }

        List<Subject> subjectsOfCourse =
                subjectRepository.findByCourseId(courseId);

        List<Score> approvedScores =
                scoreRepository.findApprovedByStudentAndCourse(studentId, courseId);

        if (approvedScores.size() == subjectsOfCourse.size()) {
            throw new IllegalStateException(
                    "Student already passed all subjects of this course");
        }

        boolean firstEnrollment =
                !enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId);

        List<Subject> subjectsToEnroll = new ArrayList<>();

        for (Subject subject : subjectsOfCourse) {

            if (firstEnrollment && subject.getYear() != 1) {
                continue;
            }

            boolean alreadyApproved = false;

            for (Score score : approvedScores) {
                if (score.getSubjectId().equals(subject.getId())) {
                    alreadyApproved = true;
                    break;
                }
            }

            if (alreadyApproved) {
                continue;
            }

            subjectsToEnroll.add(subject);
        }

        if (subjectsToEnroll.isEmpty()) {
            throw new IllegalStateException(
                    "No pending subjects to enroll");
        }

        Enrollment saved = enrollmentRepository.save(enrollment);

        for (Subject subject : subjectsToEnroll) {

            Score score = new Score();
            score.setEnrollmentId(saved.getId());
            score.setSubjectId(subject.getId());
            score.setScore(null);

            scoreRepository.save(score);
        }

        return saved;
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

}
