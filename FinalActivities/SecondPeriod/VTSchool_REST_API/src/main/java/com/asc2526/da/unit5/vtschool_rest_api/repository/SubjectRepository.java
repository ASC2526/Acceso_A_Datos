package com.asc2526.da.unit5.vtschool_rest_api.repository;

import com.asc2526.da.unit5.vtschool_rest_api.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {

    @Query("""
        SELECT s
        FROM Subject s
        JOIN SubjectCourse sc ON s.id = sc.subjectId
        WHERE sc.courseId = :courseId
    """)
    List<Subject> findByCourseId(Integer courseId);
}
