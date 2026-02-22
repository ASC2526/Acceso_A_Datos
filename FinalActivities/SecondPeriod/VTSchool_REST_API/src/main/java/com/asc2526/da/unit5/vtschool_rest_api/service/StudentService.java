package com.asc2526.da.unit5.vtschool_rest_api.service;

import com.asc2526.da.unit5.vtschool_rest_api.entity.Student;
import com.asc2526.da.unit5.vtschool_rest_api.exception.StudentAlreadyExistsException;
import com.asc2526.da.unit5.vtschool_rest_api.exception.StudentNotFoundException;
import com.asc2526.da.unit5.vtschool_rest_api.repository.EnrollmentRepository;
import com.asc2526.da.unit5.vtschool_rest_api.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;

    public StudentService(
            StudentRepository studentRepository,
            EnrollmentRepository enrollmentRepository
    ) {
        this.studentRepository = studentRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Student findById(String idcard) {

        if (idcard == null) {
            throw new IllegalArgumentException("Idcard is required");
        }

        return studentRepository.findById(idcard)
                .orElseThrow(() -> new StudentNotFoundException(idcard));
    }


    @Transactional
    public List<Student> create(List<Student> students) {

        if (students == null || students.isEmpty()) {
            throw new IllegalArgumentException("Student list cannot be empty");
        }

        for (Student student : students) {

            if (student.getIdcard() == null || student.getIdcard().isBlank()) {
                throw new IllegalArgumentException("Student idcard is required");
            }

            if (studentRepository.existsById(student.getIdcard())) {
                throw new StudentAlreadyExistsException(student.getIdcard());
            }
        }

        return studentRepository.saveAll(students);
    }


    public Student update(String idcard, Student updatedStudent) {

        if (idcard == null || updatedStudent == null) {
            throw new IllegalArgumentException("Invalid data");
        }

        Student existing = studentRepository.findById(idcard)
                .orElseThrow(() ->
                        new StudentNotFoundException(idcard));

        if (!idcard.equals(updatedStudent.getIdcard())) {
            throw new IllegalArgumentException("Idcard cannot be modified");
        }

        existing.setFirstname(updatedStudent.getFirstname());
        existing.setLastname(updatedStudent.getLastname());
        existing.setEmail(updatedStudent.getEmail());
        existing.setPhone(updatedStudent.getPhone());
        existing.setAddress(updatedStudent.getAddress());

        return studentRepository.save(existing);
    }

    public void delete(String idcard) {

        if (idcard == null) {
            throw new IllegalArgumentException("Idcard is required");
        }

        if (!studentRepository.existsById(idcard)) {
            throw new StudentNotFoundException(idcard);
        }

        boolean hasEnrollments =
                enrollmentRepository.existsByStudentId(idcard);

        if (hasEnrollments) {
            throw new IllegalStateException(
                    "Student has enrollments and cannot be deleted"
            );
        }

        studentRepository.deleteById(idcard);
    }


    public Optional<Student> findByEmail(String email) {
        return Optional.ofNullable(studentRepository.findByEmail(email));
    }
}
