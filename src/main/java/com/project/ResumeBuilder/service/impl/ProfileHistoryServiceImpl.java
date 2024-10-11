package com.project.ResumeBuilder.service.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ResumeBuilder.dtos.ProfileHistoryResponse;
import com.project.ResumeBuilder.entities.ProfileHistory;
import com.project.ResumeBuilder.enums.ChangeType;
import com.project.ResumeBuilder.repository.ProfileHistoryRepository;
import com.project.ResumeBuilder.service.ProfileHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileHistoryServiceImpl implements ProfileHistoryService {

    @Autowired
    private ProfileHistoryRepository profileHistoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public List<ProfileHistoryResponse> getHistoryByProfileId(Long profileId) {
        List<ProfileHistory> historyList = profileHistoryRepository.findByProfileId(profileId);
        return historyList.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProfileHistoryResponse> getHistoryByUserId(Long userId) {
        List<ProfileHistory> historyList = profileHistoryRepository.findByUserId(userId);
        return historyList.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProfileHistoryResponse> getHistoryByProfileIdAndDateRange(Long profileId, String startDateStr, String endDateStr) {
        // Parse the input dates without time component
        LocalDate startDate = LocalDate.parse(startDateStr.trim(), dateFormatter);
        LocalDate endDate = LocalDate.parse(endDateStr.trim(), dateFormatter);

        // Set time to start of the day for startDate and end of the day for endDate
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        // Fetch history between the start and end of the day
        List<ProfileHistory> historyList = profileHistoryRepository.findByProfileIdAndTimestampBetween(profileId, startDateTime, endDateTime);
        return historyList.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProfileHistoryResponse> getHistoryByProfileIdAndField(Long profileId, String field) {
        List<ProfileHistory> historyList = profileHistoryRepository.findByProfileIdAndChangedField(profileId, field);
        return historyList.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProfileHistoryResponse getLastChangeByProfileId(Long profileId) {
        ProfileHistory history = profileHistoryRepository.findFirstByProfileIdOrderByTimestampDesc(profileId);
        return convertToResponse(history);
    }

    @Override
    public List<ProfileHistoryResponse> getHistoryByChangeType(Long profileId, String changeTypeStr) {
        ChangeType changeType = mapStringToChangeType(changeTypeStr);  // Convert string to enum
        List<ProfileHistory> historyList = profileHistoryRepository.findByProfileIdAndChangeType(profileId, changeType);
        return historyList.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // Helper method to map the incoming String to a ChangeType enum
    private ChangeType mapStringToChangeType(String changeTypeStr) {
        try {
            return ChangeType.valueOf(changeTypeStr.toUpperCase()); // Convert string to enum
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid change type: " + changeTypeStr);
        }
    }

    @Override
    public List<ProfileHistoryResponse> getAllHistory() {
        List<ProfileHistory> historyList = profileHistoryRepository.findAll();
        return historyList.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // Convert ProfileHistory to ProfileHistoryResponse with deserialized values
    private ProfileHistoryResponse convertToResponse(ProfileHistory history) {
        ProfileHistoryResponse response = new ProfileHistoryResponse();
        response.setHistoryId(history.getHistoryId());
        response.setProfileId(history.getProfileId());
        response.setUserId(history.getUserId());
        response.setChangeType(history.getChangeType().name());
        response.setChangedField(history.getChangedField());

        // Deserialize previousValue and currentValue from JSON string to Object
        response.setPreviousValue(deserializeJsonString(history.getPreviousValue()));
        response.setCurrentValue(deserializeJsonString(history.getCurrentValue()));
        response.setTimestamp(history.getTimestamp());

        return response;
    }

    // Deserialize JSON string to Object
    private Object deserializeJsonString(String jsonString) {
        try {
            return objectMapper.readValue(jsonString, Object.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing JSON", e);
        }
    }
}