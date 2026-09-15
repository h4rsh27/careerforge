package com.careerforge.service;

import com.careerforge.dto.SkillGapResponse;
import com.careerforge.dto.SkillGapSkillResponse;
import com.careerforge.entity.JobRole;
import com.careerforge.entity.RequiredSkill;
import com.careerforge.entity.Resume;
import com.careerforge.entity.StudentProfile;
import com.careerforge.entity.User;
import com.careerforge.exception.ResourceNotFoundException;
import com.careerforge.repository.JobRoleRepository;
import com.careerforge.repository.RequiredSkillRepository;
import com.careerforge.repository.ResumeRepository;
import com.careerforge.repository.StudentProfileRepository;
import com.careerforge.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Service
public class SkillGapService {

    private final JobRoleRepository jobRoleRepository;
    private final RequiredSkillRepository requiredSkillRepository;
    private final ResumeRepository resumeRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final UserRepository userRepository;

    public SkillGapService(
            JobRoleRepository jobRoleRepository,
            RequiredSkillRepository requiredSkillRepository,
            ResumeRepository resumeRepository,
            StudentProfileRepository studentProfileRepository,
            UserRepository userRepository) {

        this.jobRoleRepository = jobRoleRepository;
        this.requiredSkillRepository =
                requiredSkillRepository;
        this.resumeRepository = resumeRepository;
        this.studentProfileRepository =
                studentProfileRepository;
        this.userRepository = userRepository;
    }

    public SkillGapResponse analyzeSkillGap(
            String email,
            Long jobRoleId) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        StudentProfile profile =
                studentProfileRepository.findByUser(user)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Student profile not found"));

        Resume resume =
                resumeRepository.findByProfile(profile)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Resume not found"));

        String extractedText =
                resume.getExtractedText();

        if (extractedText == null ||
                extractedText.isBlank()) {

            throw new ResourceNotFoundException(
                    "No extracted text found for resume");
        }

        JobRole jobRole =
                jobRoleRepository.findById(jobRoleId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Job role not found"));

        List<RequiredSkill> requiredSkills =
                requiredSkillRepository
                        .findByJobRole(jobRole);

        if (requiredSkills.isEmpty()) {

            throw new ResourceNotFoundException(
                    "No required skills found for this job role");
        }

        String normalizedResumeText =
                extractedText.toLowerCase(Locale.ROOT);

        Set<String> studentSkills =
                extractStudentSkillNames(
                        normalizedResumeText,
                        requiredSkills
                );

        List<SkillGapSkillResponse> matchedSkills =
                new ArrayList<>();

        List<SkillGapSkillResponse> missingSkills =
                new ArrayList<>();

        int totalImportance = 0;
        int matchedImportance = 0;

        for (RequiredSkill requiredSkill :
                requiredSkills) {

            int importance =
                    requiredSkill.getImportance();

            totalImportance += importance;

            String skillName =
                    requiredSkill.getSkillName();

            if (studentSkills.contains(
                    skillName.toLowerCase(Locale.ROOT))) {

                matchedImportance += importance;

                matchedSkills.add(
                        new SkillGapSkillResponse(
                                skillName,
                                importance,
                                "MATCHED"
                        )
                );

            } else {

                missingSkills.add(
                        new SkillGapSkillResponse(
                                skillName,
                                importance,
                                "MISSING"
                        )
                );
            }
        }

        double readinessPercentage =
                (matchedImportance * 100.0)
                        / totalImportance;

        readinessPercentage =
                Math.round(
                        readinessPercentage * 100.0
                ) / 100.0;

        return new SkillGapResponse(
                jobRole.getId(),
                jobRole.getRoleName(),
                matchedSkills,
                missingSkills,
                readinessPercentage
        );
    }

    private Set<String> extractStudentSkillNames(
            String resumeText,
            List<RequiredSkill> requiredSkills) {

        Set<String> studentSkills =
                new HashSet<>();

        for (RequiredSkill requiredSkill :
                requiredSkills) {

            String skillName =
                    requiredSkill.getSkillName();

            if (containsSkill(
                    resumeText,
                    skillName)) {

                studentSkills.add(
                        skillName.toLowerCase(Locale.ROOT)
                );
            }
        }

        return studentSkills;
    }

    private boolean containsSkill(
            String resumeText,
            String skillName) {

        String normalizedSkill =
                skillName.toLowerCase(Locale.ROOT);

        String searchableText =
                " " + resumeText + " ";

        String pattern =
                "(?<![a-z0-9+#.])"
                        + java.util.regex.Pattern.quote(
                        normalizedSkill)
                        + "(?![a-z0-9+#.])";

        return java.util.regex.Pattern
                .compile(pattern)
                .matcher(searchableText)
                .find();
    }
}