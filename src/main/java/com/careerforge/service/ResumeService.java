package com.careerforge.service;

import com.careerforge.dto.ResumeResponse;
import com.careerforge.entity.Resume;
import com.careerforge.entity.StudentProfile;
import com.careerforge.entity.User;
import com.careerforge.exception.ResourceNotFoundException;
import com.careerforge.repository.ResumeRepository;
import com.careerforge.repository.StudentProfileRepository;
import com.careerforge.repository.UserRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final UserRepository userRepository;

    private final Path uploadDirectory =
            Paths.get("uploads/resumes");

    public ResumeService(
            ResumeRepository resumeRepository,
            StudentProfileRepository studentProfileRepository,
            UserRepository userRepository) {

        this.resumeRepository = resumeRepository;
        this.studentProfileRepository = studentProfileRepository;
        this.userRepository = userRepository;
    }

    // =========================
    // UPLOAD / REPLACE RESUME
    // =========================

    public ResumeResponse uploadResume(
            String email,
            MultipartFile file) {

        // 1. Check whether file exists
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException(
                    "Resume file is required");
        }

        // 2. Check file type
        String contentType = file.getContentType();

        if (!"application/pdf".equalsIgnoreCase(contentType)) {
            throw new IllegalArgumentException(
                    "Only PDF resumes are allowed");
        }

        // 3. Check file size
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new IllegalArgumentException(
                    "Resume file size must not exceed 5 MB");
        }

        // 4. Find logged-in user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        // 5. Find student's profile
        StudentProfile profile =
                studentProfileRepository.findByUser(user)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Student profile not found"));

        try {

            // 6. Create upload directory if it doesn't exist
            Files.createDirectories(uploadDirectory);

            // 7. Get original filename
            String originalFileName =
                    file.getOriginalFilename();

            if (originalFileName == null ||
                    originalFileName.isBlank()) {

                originalFileName = "resume.pdf";
            }

            // 8. Generate unique storage filename
            String uniqueFileName =
                    UUID.randomUUID() + ".pdf";

            Path newFilePath =
                    uploadDirectory.resolve(uniqueFileName);

            // 9. Save the new PDF
            Files.copy(
                    file.getInputStream(),
                    newFilePath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            // 10. Check whether student already has a resume
            Resume resume =
                    resumeRepository.findByProfile(profile)
                            .orElse(new Resume());

            // 11. Delete old physical file if replacing
            String oldStoragePath =
                    resume.getStoragePath();

            if (oldStoragePath != null &&
                    !oldStoragePath.isBlank()) {

                Files.deleteIfExists(
                        Paths.get(oldStoragePath)
                );
            }

            // 12. Store new resume metadata
            resume.setFileName(originalFileName);
            resume.setFileType(contentType);
            resume.setFileSize(file.getSize());
            resume.setStoragePath(newFilePath.toString());
            resume.setUploadedAt(LocalDateTime.now());
            resume.setProfile(profile);

            // 13. Save metadata in PostgreSQL
            Resume savedResume =
                    resumeRepository.save(resume);

            // 14. Return safe response
            return mapToResponse(savedResume);

        } catch (IOException exception) {

            throw new RuntimeException(
                    "Failed to store resume file");
        }
    }

    // =========================
    // GET RESUME METADATA
    // =========================

    public ResumeResponse getResume(String email) {

        StudentProfile profile =
                getStudentProfile(email);

        Resume resume =
                resumeRepository.findByProfile(profile)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Resume not found"));

        return mapToResponse(resume);
    }

    // =========================
    // DOWNLOAD RESUME
    // =========================

    public Resource downloadResume(String email) {

        StudentProfile profile =
                getStudentProfile(email);

        Resume resume =
                resumeRepository.findByProfile(profile)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Resume not found"));

        try {

            Path filePath =
                    Paths.get(resume.getStoragePath());

            Resource resource =
                    new UrlResource(filePath.toUri());

            if (!resource.exists() ||
                    !resource.isReadable()) {

                throw new ResourceNotFoundException(
                        "Resume file not found");
            }

            return resource;

        } catch (IOException exception) {

            throw new RuntimeException(
                    "Failed to load resume file");
        }
    }

    // =========================
    // GET ORIGINAL FILE NAME
    // =========================

    public String getResumeFileName(String email) {

        StudentProfile profile =
                getStudentProfile(email);

        Resume resume =
                resumeRepository.findByProfile(profile)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Resume not found"));

        return resume.getFileName();
    }

    // =========================
    // DELETE RESUME
    // =========================

    public void deleteResume(String email) {

        StudentProfile profile =
                getStudentProfile(email);

        Resume resume =
                resumeRepository.findByProfile(profile)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Resume not found"));

        try {

            Path filePath =
                    Paths.get(resume.getStoragePath());

            Files.deleteIfExists(filePath);

            resumeRepository.delete(resume);

        } catch (IOException exception) {

            throw new RuntimeException(
                    "Failed to delete resume file");
        }
    }

    // =========================
    // FIND STUDENT PROFILE
    // =========================

    private StudentProfile getStudentProfile(
            String email) {

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"));

        return studentProfileRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student profile not found"));
    }

    // =========================
    // ENTITY → DTO
    // =========================

    private ResumeResponse mapToResponse(
            Resume resume) {

        return new ResumeResponse(
                resume.getId(),
                resume.getFileName(),
                resume.getFileType(),
                resume.getFileSize(),
                resume.getUploadedAt()
        );
    }
}