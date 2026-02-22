package com.asc2526.da.unit5.vtschool_rest_api.repository;

import com.asc2526.da.unit5.vtschool_rest_api.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {

    Optional<Enrollment> findByStudentIdAndCourseId(String studentId, Integer courseId);

    List<Enrollment> findByStudentId(String studentId);

    List<Enrollment> findByCourseId(Integer courseId);

    List<Enrollment> findByYear(Integer year);

    boolean existsByStudentIdAndYear(String studentId, Integer year);

    boolean existsByStudentId(String studentId);

    boolean existsByStudentIdAndCourseId(String studentId, Integer courseId);

}
