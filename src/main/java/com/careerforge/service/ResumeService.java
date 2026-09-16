package com.careerforge.service;

import com.careerforge.dto.ResumeResponse;
import com.careerforge.dto.ResumeTextResponse;
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
    private final ResumeTextExtractionService resumeTextExtractionService;

    private final Path uploadDirectory =
            Paths.get("uploads/resumes");

    public ResumeService(
            ResumeRepository resumeRepository,
            StudentProfileRepository studentProfileRepository,
            UserRepository userRepository,
            ResumeTextExtractionService resumeTextExtractionService) {

        this.resumeRepository = resumeRepository;
        this.studentProfileRepository =
                studentProfileRepository;
        this.userRepository = userRepository;
        this.resumeTextExtractionService =
                resumeTextExtractionService;
    }

    public ResumeResponse uploadResume(
            String email,
            MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException(
                    "Resume file is required");
        }

        String contentType = file.getContentType();

        if (!"application/pdf".equalsIgnoreCase(contentType)) {
            throw new IllegalArgumentException(
                    "Only PDF resumes are allowed");
        }

        if (file.getSize() > 5 * 1024 * 1024) {
            throw new IllegalArgumentException(
                    "Resume file size must not exceed 5 MB");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        StudentProfile profile =
                studentProfileRepository.findByUser(user)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Student profile not found"));

        try {
            Files.createDirectories(uploadDirectory);

            String originalFileName =
                    file.getOriginalFilename();

            if (originalFileName == null ||
                    originalFileName.isBlank()) {

                originalFileName = "resume.pdf";
            }

            String uniqueFileName =
                    UUID.randomUUID() + ".pdf";

            Path newFilePath =
                    uploadDirectory.resolve(uniqueFileName);

            Files.copy(
                    file.getInputStream(),
                    newFilePath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            // Extract text from uploaded PDF
            String extractedText =
                    resumeTextExtractionService.extractText(
                            newFilePath
                    );

            Resume resume =
                    resumeRepository.findByProfile(profile)
                            .orElse(new Resume());

            String oldStoragePath =
                    resume.getStoragePath();

            if (oldStoragePath != null &&
                    !oldStoragePath.isBlank()) {

                Files.deleteIfExists(
                        Paths.get(oldStoragePath)
                );
            }

            resume.setFileName(originalFileName);
            resume.setFileType(contentType);
            resume.setFileSize(file.getSize());
            resume.setStoragePath(newFilePath.toString());
            resume.setExtractedText(extractedText);
            resume.setUploadedAt(LocalDateTime.now());
            resume.setProfile(profile);

            Resume savedResume =
                    resumeRepository.save(resume);

            return mapToResponse(savedResume);

        } catch (IOException exception) {

            throw new RuntimeException(
                    "Failed to store resume file");
        }
    }

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

    /*
     * Returns the text extracted from the student's resume.
     */
    public ResumeTextResponse getResumeText(String email) {

        StudentProfile profile =
                getStudentProfile(email);

        Resume resume =
                resumeRepository.findByProfile(profile)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Resume not found"));

        return new ResumeTextResponse(
                resume.getId(),
                resume.getFileName(),
                resume.getExtractedText()
        );
    }

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

    private StudentProfile getStudentProfile(String email) {

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

    private ResumeResponse mapToResponse(Resume resume) {

        return new ResumeResponse(
                resume.getId(),
                resume.getFileName(),
                resume.getFileType(),
                resume.getFileSize(),
                resume.getUploadedAt()
        );
    }
}