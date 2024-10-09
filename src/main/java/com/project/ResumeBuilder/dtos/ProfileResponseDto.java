package com.project.ResumeBuilder.dtos;

import jakarta.validation.Valid;

import java.time.LocalDateTime;

public class ProfileResponseDto {
    private Integer userId;
    private String profileName;
    private String contactNo;
    private String objective;
    private LocalDateTime createdAt;
    private Boolean isDeleted;
    // private LocalDateTime updatedAt;

    @Valid
    private ProfileDataDto profileData;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public @Valid ProfileDataDto getProfileData() {
        return profileData;
    }

    public void setProfileData(@Valid ProfileDataDto profileData) {
        this.profileData = profileData;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}


