package com.asc2526.da.unit5.vtschool_rest_api.repository;

import com.asc2526.da.unit5.vtschool_rest_api.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, String> {
    Student findByEmail(String username);
}
