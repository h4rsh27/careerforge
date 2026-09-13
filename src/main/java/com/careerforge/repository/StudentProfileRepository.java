package com.careerforge.repository;

import com.careerforge.entity.StudentProfile;
import com.careerforge.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentProfileRepository
        extends JpaRepository<StudentProfile, Long> {

    Optional<StudentProfile> findByUser(User user);

    boolean existsByUser(User user);
}