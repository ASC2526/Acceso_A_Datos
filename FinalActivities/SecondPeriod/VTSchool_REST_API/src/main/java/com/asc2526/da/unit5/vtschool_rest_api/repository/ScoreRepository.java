package com.asc2526.da.unit5.vtschool_rest_api.repository;

import com.asc2526.da.unit5.vtschool_rest_api.entity.Score;
import com.asc2526.da.unit5.vtschool_rest_api.web.dto.ScoreDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface ScoreRepository extends JpaRepository<Score, Integer> {

    List<Score> findByEnrollmentId(Integer enrollmentId);

    boolean existsByEnrollmentId(Integer enrollmentId);

    @Query("""
        SELECT s FROM Score s
        JOIN Enrollment e ON s.enrollmentId = e.id
        WHERE e.studentId = :studentId
        AND e.courseId = :courseId
        AND s.score >= 5
    """)
    List<Score> findApprovedByStudentAndCourse(
            String studentId,
            Integer courseId);

    @Query("""
        SELECT s FROM Score s
        JOIN Enrollment e ON s.enrollmentId = e.id
        WHERE e.studentId = :studentId
        AND e.courseId = :courseId
        AND s.score IS NULL
    """)
    List<Score> findPendingByStudentAndCourse(
            String studentId,
            Integer courseId
    );

    Optional<Score> findByEnrollmentIdAndSubjectId(Integer enrollmentId, Integer subjectId);
    @Query("""
SELECT new com.asc2526.da.unit5.vtschool_rest_api.web.dto.ScoreDTO(
    s.id,
    sub.name,
    sub.year,
    e.year,
    s.score
)
FROM Score s
JOIN Enrollment e ON s.enrollmentId = e.id
JOIN Subject sub ON s.subjectId = sub.id
WHERE e.studentId = :studentId
AND (:courseId IS NULL OR e.courseId = :courseId)
ORDER BY e.year DESC, sub.year ASC
""")
    List<ScoreDTO> findScoresForStudent(
            String studentId,
            Integer courseId
    );
}