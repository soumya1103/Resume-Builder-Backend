package com.project.ResumeBuilder.service.impl;
import com.project.ResumeBuilder.constants.ProfileConstants;
import com.project.ResumeBuilder.dtos.CommonResponseDto;
import com.project.ResumeBuilder.dtos.ProfileDto;
import com.project.ResumeBuilder.dtos.ProfileResponseDto;
import com.project.ResumeBuilder.dtos.ProfileUpdateDto;
import com.project.ResumeBuilder.entities.Profile;
import com.project.ResumeBuilder.exception.NotFoundException;
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

        profile.setProfileName(profileDto.getProfileName());
        profile.setContactNo(profileDto.getContactNo());
        profile.setObjective(profileDto.getObjective());
        profile.setProfileData(profileDto.getProfileData());
        profileRepository.save(profile);
        CommonResponseDto message=new CommonResponseDto();
        message.setMessage(ProfileConstants.PROFILE_UPDATED_SUCCESSFULLY);
        return message;
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

    public ProfileResponseDto getProfileByUserId(Long userId) {
        Optional<Profile> optionalProfile = profileRepository.findByUserId(userId);
        Profile profile = optionalProfile.orElseThrow(() -> new NotFoundException(ProfileConstants.USER_NOT_FOUND +  userId));
        return convertToResponseDto(profile);
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