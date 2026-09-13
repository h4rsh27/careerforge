package com.careerforge.controller;

import com.careerforge.dto.UserRequest;
import com.careerforge.dto.UserResponse;
import com.careerforge.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // =========================
    // REGISTER USER
    // =========================

    @PostMapping
    public UserResponse createUser(
            @Valid @RequestBody UserRequest request) {

        return userService.createUser(request);
    }

    // =========================
    // GET CURRENT LOGGED-IN USER
    // =========================

    @GetMapping("/me")
    public UserResponse getCurrentUser(
            Authentication authentication) {

        String email = authentication.getName();

        return userService.getCurrentUser(email);
    }
}