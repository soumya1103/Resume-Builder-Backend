package com.project.ResumeBuilder.service;

import com.project.ResumeBuilder.dtos.ProfileHistoryResponse;
import com.project.ResumeBuilder.entities.ProfileHistory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface ProfileHistoryService {
    List<ProfileHistoryResponse> getHistoryByProfileId(Long profileId);

    List<ProfileHistoryResponse> getHistoryByUserId(Long userId);

    List<ProfileHistoryResponse> getHistoryByProfileIdAndField(Long profileId, String field);

    ProfileHistoryResponse getLastChangeByProfileId(Long profileId);

    List<ProfileHistoryResponse> getHistoryByChangeType(Long profileId, String changeType);

    List<ProfileHistoryResponse> getAllHistory();

    List<ProfileHistoryResponse> getHistoryByProfileIdAndDateRange(Long profileId, String startDateStr, String endDateStr);
}
