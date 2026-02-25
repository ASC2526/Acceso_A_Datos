package com.asc2526.da.unit5.vtschool_rest_api.repository;

import com.asc2526.da.unit5.vtschool_rest_api.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, String> {
    Student findByEmail(String username);

    @Query("""
        SELECT DISTINCT st FROM Student st
        JOIN Enrollment e ON e.studentId = st.idcard
        JOIN Score s ON s.enrollmentId = e.id
        WHERE s.score IS null
        ORDER BY st.lastname, st.firstname
    """)
    List<Student> findStudentsWithNullScores();

}
