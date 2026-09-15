package com.careerforge.repository;

import com.careerforge.entity.JobRole;
import com.careerforge.entity.RequiredSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequiredSkillRepository
        extends JpaRepository<RequiredSkill, Long> {

    List<RequiredSkill> findByJobRole(JobRole jobRole);

    boolean existsByJobRoleAndSkillNameIgnoreCase(
            JobRole jobRole,
            String skillName);
}