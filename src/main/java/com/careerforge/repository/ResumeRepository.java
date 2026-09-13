package com.careerforge.repository;

import com.careerforge.entity.Resume;
import com.careerforge.entity.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResumeRepository extends JpaRepository<Resume, Long> {

    Optional<Resume> findByProfile(StudentProfile profile);

    boolean existsByProfile(StudentProfile profile);
}