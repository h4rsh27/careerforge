package com.careerforge.service;

import com.careerforge.dto.SkillRequest;
import com.careerforge.dto.SkillResponse;
import com.careerforge.entity.StudentProfile;
import com.careerforge.entity.StudentSkill;
import com.careerforge.entity.User;
import com.careerforge.exception.DuplicateSkillException;
import com.careerforge.exception.ResourceNotFoundException;
import com.careerforge.repository.StudentProfileRepository;
import com.careerforge.repository.StudentSkillRepository;
import com.careerforge.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class StudentSkillService {

    private final StudentSkillRepository studentSkillRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final UserRepository userRepository;

    public StudentSkillService(
            StudentSkillRepository studentSkillRepository,
            StudentProfileRepository studentProfileRepository,
            UserRepository userRepository) {

        this.studentSkillRepository = studentSkillRepository;
        this.studentProfileRepository = studentProfileRepository;
        this.userRepository = userRepository;
    }

    public SkillResponse createSkill(
            String email,
            SkillRequest request) {

        StudentProfile profile = getStudentProfile(email);

        if (studentSkillRepository
                .existsByProfileAndSkillNameIgnoreCase(
                        profile,
                        request.getSkillName().trim())) {

            throw new DuplicateSkillException(
                    "Skill already exists");
        }

        StudentSkill skill = new StudentSkill();

        skill.setSkillName(request.getSkillName().trim());
        skill.setProficiencyLevel(request.getProficiencyLevel());
        skill.setProfile(profile);

        StudentSkill savedSkill =
                studentSkillRepository.save(skill);

        return mapToResponse(savedSkill);
    }

    public List<SkillResponse> getMySkills(String email) {

        StudentProfile profile = getStudentProfile(email);

        return studentSkillRepository
                .findByProfile(profile)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public SkillResponse updateSkill(
            String email,
            Long skillId,
            SkillRequest request) {

        StudentProfile profile = getStudentProfile(email);

        StudentSkill skill =
                studentSkillRepository
                        .findByIdAndProfile(skillId, profile)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Skill not found"));

        String newSkillName = request.getSkillName().trim();

        boolean duplicateExists =
                studentSkillRepository
                        .existsByProfileAndSkillNameIgnoreCase(
                                profile,
                                newSkillName);

        if (duplicateExists &&
                !skill.getSkillName().equalsIgnoreCase(newSkillName)) {

            throw new DuplicateSkillException(
                    "Skill already exists");
        }

        skill.setSkillName(newSkillName);
        skill.setProficiencyLevel(
                request.getProficiencyLevel());

        StudentSkill updatedSkill =
                studentSkillRepository.save(skill);

        return mapToResponse(updatedSkill);
    }

    public void deleteSkill(
            String email,
            Long skillId) {

        StudentProfile profile = getStudentProfile(email);

        StudentSkill skill =
                studentSkillRepository
                        .findByIdAndProfile(skillId, profile)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Skill not found"));

        studentSkillRepository.delete(skill);
    }

    private StudentProfile getStudentProfile(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        return studentProfileRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student profile not found"));
    }

    private SkillResponse mapToResponse(
            StudentSkill skill) {

        return new SkillResponse(
                skill.getId(),
                skill.getSkillName(),
                skill.getProficiencyLevel()
        );
    }
}