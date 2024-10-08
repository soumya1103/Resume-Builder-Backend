package com.project.ResumeBuilder.service.impl;
import com.project.ResumeBuilder.dtos.CommonResponseDto;
import com.project.ResumeBuilder.dtos.ProfileDto;
import com.project.ResumeBuilder.dtos.ProfileResponseDto;
import com.project.ResumeBuilder.dtos.ProfileUpdateDto;
import com.project.ResumeBuilder.entities.Profile;
import com.project.ResumeBuilder.exception.NotFoundException;
import com.project.ResumeBuilder.repository.ProfileRepository;
import com.project.ResumeBuilder.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;


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
        message.setMessage("Created Profile Successfully");
        return message;

    }

    @Override
    public CommonResponseDto updateProfile(Long id, @Valid ProfileUpdateDto profileDto) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Profile not found with id: " + id));

        profile.setProfileName(profileDto.getProfileName());
        profile.setContactNo(profileDto.getContactNo());
        profile.setObjective(profileDto.getObjective());
        profile.setProfileData(profileDto.getProfileData());
        //  profile.setUpdatedAt(LocalDateTime.now());

        profileRepository.save(profile);
        CommonResponseDto message=new CommonResponseDto();
        message.setMessage("Updated Profile Successfully");
        return message;
    }

    @Override
    public ProfileResponseDto getProfileById(Long id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Profile not found with id: " + id));

        ProfileResponseDto responseDto = new ProfileResponseDto();
        responseDto.setUserId(profile.getUserId());
        responseDto.setProfileName(profile.getProfileName());
        responseDto.setContactNo(profile.getContactNo());
        responseDto.setObjective(profile.getObjective());
        responseDto.setCreatedAt(profile.getCreatedAt());
        // responseDto.setUpdatedAt(profile.getUpdatedAt());
        responseDto.setProfileData(profile.getProfileData());

        return responseDto;
    }

    @Override
    public void deleteProfile(Long profileId) {
        Optional<Profile> profileOptional = profileRepository.findById(profileId);
        if (profileOptional.isPresent()) {
            Profile profile = profileOptional.get();
            profile.setIsDeleted(true);
            profile.setDeletedAt(LocalDateTime.now());
            profileRepository.save(profile);
        } else {
            throw new NotFoundException("Profile with id " + profileId + " not found");
        }
    }


}