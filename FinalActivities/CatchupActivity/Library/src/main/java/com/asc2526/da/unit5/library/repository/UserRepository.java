package com.asc2526.da.unit5.library.repository;

import com.asc2526.da.unit5.library.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findBySurname(String surname);
}
