package com.asc2526.da.unit5.vtschool_rest_api.repository;

import com.asc2526.da.unit5.vtschool_rest_api.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    @Query("""
        SELECT DISTINCT c
        FROM Course c
        JOIN Enrollment e ON e.courseId = c.id
        WHERE e.studentId = :studentId
    """)
    List<Course> findCoursesEnrolledOfStudent(String studentId);
}
