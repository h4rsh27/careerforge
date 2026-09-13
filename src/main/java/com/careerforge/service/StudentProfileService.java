package com.careerforge.service;

import com.careerforge.dto.StudentProfileRequest;
import com.careerforge.dto.StudentProfileResponse;
import com.careerforge.entity.StudentProfile;
import com.careerforge.entity.User;
import com.careerforge.exception.ResourceNotFoundException;
import com.careerforge.repository.StudentProfileRepository;
import com.careerforge.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentProfileService {

    private final StudentProfileRepository studentProfileRepository;
    private final UserRepository userRepository;

    public StudentProfileService(
            StudentProfileRepository studentProfileRepository,
            UserRepository userRepository) {

        this.studentProfileRepository = studentProfileRepository;
        this.userRepository = userRepository;
    }

    // =========================
    // CREATE STUDENT PROFILE
    // =========================

    public StudentProfileResponse createProfile(
            String email,
            StudentProfileRequest request) {

        // Find logged-in user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        // Check whether profile already exists
        if (studentProfileRepository.existsByUser(user)) {
            throw new RuntimeException(
                    "Student profile already exists");
        }

        // Create profile entity
        StudentProfile profile = new StudentProfile();

        profile.setPhone(request.getPhone());
        profile.setCollege(request.getCollege());
        profile.setDegree(request.getDegree());
        profile.setBranch(request.getBranch());
        profile.setGraduationYear(
                request.getGraduationYear());
        profile.setLocation(request.getLocation());
        profile.setBio(request.getBio());

        // Connect profile with logged-in user
        profile.setUser(user);

        // Save to PostgreSQL
        StudentProfile savedProfile =
                studentProfileRepository.save(profile);

        // Convert Entity → Response DTO
        return new StudentProfileResponse(
                savedProfile.getId(),
                savedProfile.getPhone(),
                savedProfile.getCollege(),
                savedProfile.getDegree(),
                savedProfile.getBranch(),
                savedProfile.getGraduationYear(),
                savedProfile.getLocation(),
                savedProfile.getBio()
        );
    }
    // =========================
// GET STUDENT PROFILE
// =========================

    public StudentProfileResponse getProfile(String email) {

        // Find logged-in user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student profile not found"));

        // Find profile belonging to this user
        StudentProfile profile =
                studentProfileRepository.findByUser(user)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Student profile not found"));

        // Convert Entity → Response DTO
        return new StudentProfileResponse(
                profile.getId(),
                profile.getPhone(),
                profile.getCollege(),
                profile.getDegree(),
                profile.getBranch(),
                profile.getGraduationYear(),
                profile.getLocation(),
                profile.getBio()
        );
    }
    // =========================
// UPDATE STUDENT PROFILE
// =========================

    public StudentProfileResponse updateProfile(
            String email,
            StudentProfileRequest request) {

        // Find logged-in user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        // Find existing profile
        StudentProfile profile =
                studentProfileRepository.findByUser(user)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Student profile not found"));

        // Update profile fields
        profile.setPhone(request.getPhone());
        profile.setCollege(request.getCollege());
        profile.setDegree(request.getDegree());
        profile.setBranch(request.getBranch());
        profile.setGraduationYear(
                request.getGraduationYear());
        profile.setLocation(request.getLocation());
        profile.setBio(request.getBio());

        // Save updated profile
        StudentProfile updatedProfile =
                studentProfileRepository.save(profile);

        // Entity → DTO
        return new StudentProfileResponse(
                updatedProfile.getId(),
                updatedProfile.getPhone(),
                updatedProfile.getCollege(),
                updatedProfile.getDegree(),
                updatedProfile.getBranch(),
                updatedProfile.getGraduationYear(),
                updatedProfile.getLocation(),
                updatedProfile.getBio()
        );
    }
    // =========================
// DELETE STUDENT PROFILE
// =========================

    public void deleteProfile(String email) {

        // Find logged-in user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        // Find profile
        StudentProfile profile =
                studentProfileRepository.findByUser(user)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Student profile not found"));

        // Delete profile
        studentProfileRepository.delete(profile);
    }
}