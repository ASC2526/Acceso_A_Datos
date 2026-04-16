package com.asc2526.da.unit5.library.repository;

import com.asc2526.da.unit5.library.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}