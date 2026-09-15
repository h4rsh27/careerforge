package com.careerforge.repository;

import com.careerforge.entity.JobRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobRoleRepository
        extends JpaRepository<JobRole, Long> {

    Optional<JobRole> findByRoleNameIgnoreCase(
            String roleName);

    boolean existsByRoleNameIgnoreCase(
            String roleName);
}