package com.project.ResumeBuilder.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public class ProfileDataDto {


    @NotEmpty(message = "Professional summary cannot be empty")
    private List<@NotBlank(message = "Professional summary can not be null") String> professionalSummary;

    @NotEmpty(message = "Certificates cannot be empty")
    private List<@NotBlank(message = "Certificates cannot be blank") String> certificates;

    @Valid
    private TechnicalSkillsDto technicalSkills;

    @Valid
    @NotEmpty(message = "Professional experience cannot be empty")
    private List<ProfessionalExperienceDto> professionalExperience;

    @Valid
    @NotEmpty(message = "Education cannot be empty")
    private List<EducationDto> education;

    public List<String> getProfessionalSummary() {
        return professionalSummary;
    }

    public void setProfessionalSummary(List<String> professionalSummary) {
        this.professionalSummary = professionalSummary;
    }

    public  List<String> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<String> certificates) {
        this.certificates = certificates;
    }

    public  TechnicalSkillsDto getTechnicalSkills() {
        return technicalSkills;
    }

    public void setTechnicalSkills(TechnicalSkillsDto technicalSkills) {
        this.technicalSkills = technicalSkills;
    }


    public  List<ProfessionalExperienceDto> getProfessionalExperience() {
        return professionalExperience;
    }

    public void setProfessionalExperience(List<ProfessionalExperienceDto> professionalExperience) {
        this.professionalExperience = professionalExperience;
    }

    public  List<EducationDto> getEducation() {
        return education;
    }

    public void setEducation(List<EducationDto> education) {
        this.education = education;
    }
}




