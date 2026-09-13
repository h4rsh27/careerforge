package com.careerforge.repository;

import com.careerforge.entity.StudentProfile;
import com.careerforge.entity.StudentSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentSkillRepository
        extends JpaRepository<StudentSkill, Long> {

    List<StudentSkill> findByProfile(StudentProfile profile);

    Optional<StudentSkill> findByIdAndProfile(
            Long id,
            StudentProfile profile
    );

    boolean existsByProfileAndSkillNameIgnoreCase(
            StudentProfile profile,
            String skillName
    );
}