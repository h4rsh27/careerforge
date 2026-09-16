package com.careerforge.repository;

import com.careerforge.entity.Company;
import com.careerforge.entity.JobListing;
import com.careerforge.entity.JobRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobListingRepository
        extends JpaRepository<JobListing, Long> {

    List<JobListing> findByCompany(Company company);

    List<JobListing> findByJobRole(JobRole jobRole);

    List<JobListing> findByLocationIgnoreCase(String location);
}