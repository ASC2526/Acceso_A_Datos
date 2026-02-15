package com.asc2526.da.unit5.vtschool_rest_api.repository;

import com.asc2526.da.unit5.vtschool_rest_api.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ScoreRepository extends JpaRepository<Score, Integer> {

    List<Score> findByEnrollmentId(Integer enrollmentId);

    Optional<Score> findByEnrollmentIdAndSubjectId(Integer enrollmentId, Integer subjectId);

    boolean existsByEnrollmentId(Integer enrollmentId);

}
