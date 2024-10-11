package com.project.ResumeBuilder.service.impl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.ResumeBuilder.constants.ProfileConstants;
import com.project.ResumeBuilder.dtos.*;
import com.project.ResumeBuilder.entities.Profile;
import com.project.ResumeBuilder.entities.ProfileHistory;
import com.project.ResumeBuilder.enums.ChangeType;
import com.project.ResumeBuilder.exception.NotFoundException;
import com.project.ResumeBuilder.repository.ProfileHistoryRepository;
import com.project.ResumeBuilder.repository.ProfileRepository;
import com.project.ResumeBuilder.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileHistoryRepository profileHistoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public CommonResponseDto createProfile(@Valid ProfileDto profileDto) {
        Profile profile = new Profile();
        profile.setUserId(profileDto.getUserId());
        profile.setProfileName(profileDto.getProfileName());
        profile.setContactNo(profileDto.getContactNo());
        profile.setObjective(profileDto.getObjective());
        profile.setProfileData(profileDto.getProfileData());
        profile.setCreatedAt(LocalDateTime.now());// Set ProfileDataDto
        profileRepository.save(profile);
        CommonResponseDto message=new CommonResponseDto();
        message.setMessage(ProfileConstants.PROFILE_CREATED_SUCCESSFULLY);
        return message;

    }

    @Override
    public CommonResponseDto updateProfile(Long id, @Valid ProfileUpdateDto profileDto) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ProfileConstants.PROFILE_NOT_FOUND + id));

        // Log changes for each root-level field
        if (!profile.getProfileName().equals(profileDto.getProfileName())) {
            logProfileChange(profile, "profileName", profile.getProfileName(), profileDto.getProfileName(), ChangeType.UPDATE);
            profile.setProfileName(profileDto.getProfileName());
        }

        if (!profile.getContactNo().equals(profileDto.getContactNo())) {
            logProfileChange(profile, "contactNo", profile.getContactNo(), profileDto.getContactNo(), ChangeType.UPDATE);
            profile.setContactNo(profileDto.getContactNo());
        }

        if (!profile.getObjective().equals(profileDto.getObjective())) {
            logProfileChange(profile, "objective", profile.getObjective(), profileDto.getObjective(), ChangeType.UPDATE);
            profile.setObjective(profileDto.getObjective());
        }

        // Compare and log changes for nested profileData
        compareAndLogProfileDataChanges(profile, profileDto);

        // Update the profileData in the profile entity
        profile.setProfileData(profileDto.getProfileData());

        // Save the updated profile
        profileRepository.save(profile);

        CommonResponseDto message = new CommonResponseDto();
        message.setMessage(ProfileConstants.PROFILE_UPDATED_SUCCESSFULLY);
        return message;
    }

    private void compareAndLogProfileDataChanges(Profile profile, ProfileUpdateDto profileDto) {
        ProfileDataDto existingProfileData = profile.getProfileData();
        ProfileDataDto newProfileData = profileDto.getProfileData();

        // Compare professional experience using sorted JSON
        if (!convertToJson(existingProfileData.getProfessionalExperience()).equals(convertToJson(newProfileData.getProfessionalExperience()))) {
            String oldExperience = convertToJson(existingProfileData.getProfessionalExperience());
            String newExperience = convertToJson(newProfileData.getProfessionalExperience());

            logProfileChange(profile, "professionalExperience", oldExperience, newExperience, ChangeType.UPDATE);
        }

        // Compare professionalSummary using sorted JSON
        if (!convertToJson(existingProfileData.getProfessionalSummary()).equals(convertToJson(newProfileData.getProfessionalSummary()))) {
            logProfileChange(profile, "professionalSummary",
                    convertToJson(existingProfileData.getProfessionalSummary()),
                    convertToJson(newProfileData.getProfessionalSummary()),
                    ChangeType.UPDATE);
        }

        // Compare certificates using sorted JSON
        if (!convertToJson(existingProfileData.getCertificates()).equals(convertToJson(newProfileData.getCertificates()))) {
            logProfileChange(profile, "certificates",
                    convertToJson(existingProfileData.getCertificates()),
                    convertToJson(newProfileData.getCertificates()),
                    ChangeType.UPDATE);
        }

        // Compare technical skills as a whole object using sorted JSON
        if (!convertToJson(existingProfileData.getTechnicalSkills()).equals(convertToJson(newProfileData.getTechnicalSkills()))) {
            String oldTechnicalSkills = convertToJson(existingProfileData.getTechnicalSkills());
            String newTechnicalSkills = convertToJson(newProfileData.getTechnicalSkills());

            logProfileChange(profile, "technicalSkills", oldTechnicalSkills, newTechnicalSkills, ChangeType.UPDATE);
        }

        // Compare education using sorted JSON
        if (!convertToJson(existingProfileData.getEducation()).equals(convertToJson(newProfileData.getEducation()))) {
            logProfileChange(profile, "education",
                    convertToJson(existingProfileData.getEducation()),
                    convertToJson(newProfileData.getEducation()),
                    ChangeType.UPDATE);
        }
    }


    private String convertToJson(Object data) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            // Register the JavaTimeModule to handle Java 8+ date/time types like LocalDate, LocalDateTime
            mapper.registerModule(new JavaTimeModule());

            // Ensure keys are ordered for consistency in comparisons
            mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);

            // Exclude null values from JSON
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

            return mapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting data to JSON", e);
        }
    }

    // Logging changes in profile fields
    private void logProfileChange(Profile profile, String field, String oldValue, String newValue, ChangeType changeType) {
        ProfileHistory history = new ProfileHistory();
        history.setProfileId(profile.getId());
        history.setUserId(profile.getUserId());
        history.setChangedField(field);
        history.setPreviousValue(oldValue);
        history.setCurrentValue(newValue);
        history.setChangeType(changeType);
        history.setTimestamp(LocalDateTime.now());
        profileHistoryRepository.save(history);
    }


    @Override
    public ProfileResponseDto getProfileById(Long id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ProfileConstants.PROFILE_NOT_FOUND + id));

        ProfileResponseDto responseDto =  convertToResponseDto(profile);
        return responseDto;
    }

    @Override
    public CommonResponseDto deleteProfile(Long profileId) {
        Optional<Profile> profileOptional = profileRepository.findById(profileId);
        if (profileOptional.isPresent()) {
            Profile profile = profileOptional.get();
            profile.setIsDeleted(true);
            profile.setDeletedAt(LocalDateTime.now());
            profileRepository.save(profile);
        } else {
            throw new NotFoundException(ProfileConstants.PROFILE_NOT_FOUND + profileId);
        }
        CommonResponseDto message=new CommonResponseDto();
        message.setMessage(ProfileConstants.PROFILE_DELETED_SUCCESSFULLY);
        return message;

    }
    public List<ProfileResponseDto> getProfilesByUserId(Long userId) {
      List<Profile> profiles = profileRepository.findAllByUserId(userId);

      if (profiles.isEmpty()) {
          throw new NotFoundException(ProfileConstants.USER_NOT_FOUND + userId);
      }

      return profiles.stream()
              .map(this::convertToResponseDto)
              .collect(Collectors.toList());
  }


    public List<ProfileResponseDto> getAllProfiles() {
        List<Profile> profiles = profileRepository.findAll();
        return profiles.stream().map(this::convertToResponseDto).collect(Collectors.toList());
    }

    private ProfileResponseDto convertToResponseDto(Profile profile) {
        ProfileResponseDto responseDto = new ProfileResponseDto();
        responseDto.setId(profile.getId());
        responseDto.setUserId(profile.getUserId());
        responseDto.setProfileName(profile.getProfileName());
        responseDto.setContactNo(profile.getContactNo());
        responseDto.setObjective(profile.getObjective());
        responseDto.setCreatedAt(profile.getCreatedAt());
        responseDto.setProfileData(profile.getProfileData());
        responseDto.setIsDeleted(profile.getIsDeleted());
        return responseDto;
    }


}