package com.careerforge.dto;

public class StudentProfileResponse {

    private Long id;
    private String phone;
    private String college;
    private String degree;
    private String branch;
    private Integer graduationYear;
    private String location;
    private String bio;

    public StudentProfileResponse() {
    }

    public StudentProfileResponse(
            Long id,
            String phone,
            String college,
            String degree,
            String branch,
            Integer graduationYear,
            String location,
            String bio) {

        this.id = id;
        this.phone = phone;
        this.college = college;
        this.degree = degree;
        this.branch = branch;
        this.graduationYear = graduationYear;
        this.location = location;
        this.bio = bio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Integer getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(Integer graduationYear) {
        this.graduationYear = graduationYear;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}