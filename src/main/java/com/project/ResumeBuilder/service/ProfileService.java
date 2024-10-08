package com.project.ResumeBuilder.service;


import com.project.ResumeBuilder.dtos.CommonResponseDto;
import com.project.ResumeBuilder.dtos.ProfileDto;
import com.project.ResumeBuilder.dtos.ProfileResponseDto;
import com.project.ResumeBuilder.dtos.ProfileUpdateDto;
import com.project.ResumeBuilder.entities.Profile;

import javax.validation.Valid;


public interface ProfileService {

    CommonResponseDto createProfile(ProfileDto profileDto);
    CommonResponseDto updateProfile(Long id, @Valid ProfileUpdateDto profileDto);
    ProfileResponseDto getProfileById(Long id);
    void deleteProfile(Long profileId);


}

