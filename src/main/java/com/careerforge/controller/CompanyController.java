package com.careerforge.controller;

import com.careerforge.dto.CompanyRequest;
import com.careerforge.dto.CompanyResponse;
import com.careerforge.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(
            CompanyService companyService) {

        this.companyService = companyService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyResponse createCompany(
            @Valid @RequestBody CompanyRequest request) {

        return companyService.createCompany(request);
    }

    @GetMapping
    public List<CompanyResponse> getAllCompanies() {

        return companyService.getAllCompanies();
    }

    @GetMapping("/{id}")
    public CompanyResponse getCompany(
            @PathVariable Long id) {

        return companyService.getCompany(id);
    }

    @PutMapping("/{id}")
    public CompanyResponse updateCompany(
            @PathVariable Long id,
            @Valid @RequestBody CompanyRequest request) {

        return companyService.updateCompany(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompany(
            @PathVariable Long id) {

        companyService.deleteCompany(id);
    }
}