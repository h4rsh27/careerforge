package com.careerforge.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CareerForgeController {

    @GetMapping("/api/hello")
    public String hello() {
        return "CareerForge Backend is running";
    }
}