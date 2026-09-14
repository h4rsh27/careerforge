package com.careerforge.service;

import com.careerforge.dto.ExtractedSkillResponse;
import com.careerforge.entity.Resume;
import com.careerforge.entity.StudentProfile;
import com.careerforge.entity.User;
import com.careerforge.exception.ResourceNotFoundException;
import com.careerforge.repository.ResumeRepository;
import com.careerforge.repository.StudentProfileRepository;
import com.careerforge.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

@Service
public class ResumeSkillExtractionService {

    private final ResumeRepository resumeRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final UserRepository userRepository;

    public ResumeSkillExtractionService(
            ResumeRepository resumeRepository,
            StudentProfileRepository studentProfileRepository,
            UserRepository userRepository) {

        this.resumeRepository = resumeRepository;
        this.studentProfileRepository =
                studentProfileRepository;
        this.userRepository = userRepository;
    }

    public ExtractedSkillResponse extractSkills(String email) {

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

        String resumeText = resume.getExtractedText();

        if (resumeText == null || resumeText.isBlank()) {
            throw new ResourceNotFoundException(
                    "No extracted text found for resume");
        }

        List<String> skills =
                findSkills(resumeText);

        return new ExtractedSkillResponse(
                resume.getId(),
                resume.getFileName(),
                skills
        );
    }

    private List<String> findSkills(String resumeText) {

        String text = resumeText.toLowerCase(Locale.ROOT);

        List<String> skills = new ArrayList<>();

        addIfPresent(text, skills, "Java");
        addIfPresent(text, skills, "Spring Boot");
        addIfPresent(text, skills, "Spring");
        addIfPresent(text, skills, "Hibernate");
        addIfPresent(text, skills, "JPA");
        addIfPresent(text, skills, "PostgreSQL");
        addIfPresent(text, skills, "MySQL");
        addIfPresent(text, skills, "SQL");
        addIfPresent(text, skills, "React");
        addIfPresent(text, skills, "JavaScript");
        addIfPresent(text, skills, "HTML");
        addIfPresent(text, skills, "CSS");
        addIfPresent(text, skills, "Node.js");
        addIfPresent(text, skills, "Python");
        addIfPresent(text, skills, "Docker");
        addIfPresent(text, skills, "Git");
        addIfPresent(text, skills, "GitHub");

        return skills;
    }

    private void addIfPresent(
            String resumeText,
            List<String> skills,
            String skill) {

        String normalizedSkill =
                skill.toLowerCase(Locale.ROOT);

        String searchableText =
                " " + resumeText + " ";

        String pattern =
                "(?<![a-z0-9+#.])"
                        + Pattern.quote(normalizedSkill)
                        + "(?![a-z0-9+#.])";

        if (Pattern.compile(pattern)
                .matcher(searchableText)
                .find()) {

            skills.add(skill);
        }
    }
}