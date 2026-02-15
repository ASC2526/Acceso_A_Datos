package com.asc2526.da.unit5.vtschool_rest_api.repository;

import com.asc2526.da.unit5.vtschool_rest_api.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {

    Optional<Enrollment> findByStudentIdAndCourseId(String studentId, Integer courseId);

    List<Enrollment> findByStudentIdAndYear(String studentId, Integer year);

    List<Enrollment> findByStudentId(String studentId);

    List<Enrollment> findByCourseId(Integer courseId);

    List<Enrollment> findByYear(Integer year);

    boolean existsByStudentIdAndCourseId(String studentId, Integer courseId);

    boolean existsByStudentId(String studentId);
}
