package com.careerforge.service;

import com.careerforge.dto.CompanyRequest;
import com.careerforge.dto.CompanyResponse;
import com.careerforge.entity.Company;
import com.careerforge.exception.ResourceNotFoundException;
import com.careerforge.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public CompanyResponse createCompany(
            CompanyRequest request) {

        if (companyRepository.existsByNameIgnoreCase(
                request.getName().trim())) {

            throw new IllegalArgumentException(
                    "Company already exists");
        }

        Company company = new Company();

        company.setName(request.getName().trim());
        company.setDescription(request.getDescription());
        company.setWebsite(request.getWebsite());
        company.setLocation(request.getLocation());

        return mapToResponse(
                companyRepository.save(company)
        );
    }

    public List<CompanyResponse> getAllCompanies() {

        return companyRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public CompanyResponse getCompany(Long id) {

        Company company =
                companyRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Company not found"));

        return mapToResponse(company);
    }

    public CompanyResponse updateCompany(
            Long id,
            CompanyRequest request) {

        Company company =
                companyRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Company not found"));

        company.setName(request.getName().trim());
        company.setDescription(request.getDescription());
        company.setWebsite(request.getWebsite());
        company.setLocation(request.getLocation());

        return mapToResponse(
                companyRepository.save(company)
        );
    }

    public void deleteCompany(Long id) {

        Company company =
                companyRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Company not found"));

        companyRepository.delete(company);
    }

    private CompanyResponse mapToResponse(
            Company company) {

        return new CompanyResponse(
                company.getId(),
                company.getName(),
                company.getDescription(),
                company.getWebsite(),
                company.getLocation()
        );
    }
}