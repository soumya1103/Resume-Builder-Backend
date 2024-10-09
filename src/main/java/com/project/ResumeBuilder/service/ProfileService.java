package com.project.ResumeBuilder.service;
import com.project.ResumeBuilder.dtos.CommonResponseDto;
import com.project.ResumeBuilder.dtos.ProfileDto;
import com.project.ResumeBuilder.dtos.ProfileResponseDto;
import com.project.ResumeBuilder.dtos.ProfileUpdateDto;
import jakarta.validation.Valid;

import java.util.List;


public interface ProfileService {

    CommonResponseDto createProfile(ProfileDto profileDto);
    CommonResponseDto updateProfile(Long id, @Valid ProfileUpdateDto profileDto);
    ProfileResponseDto getProfileById(Long id);
    CommonResponseDto deleteProfile(Long profileId);
    List<ProfileResponseDto> getAllProfiles();
    ProfileResponseDto getProfileByUserId(Long id);


}

