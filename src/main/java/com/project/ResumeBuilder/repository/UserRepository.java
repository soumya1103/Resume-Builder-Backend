package com.project.ResumeBuilder.repository;

import com.project.ResumeBuilder.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByEmail(String userEmail);
}
