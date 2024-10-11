package com.project.ResumeBuilder.controller;

import com.project.ResumeBuilder.dtos.ProfileHistoryResponse;
import com.project.ResumeBuilder.entities.ProfileHistory;
import com.project.ResumeBuilder.service.ProfileHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/profile-history")
public class ProfileHistoryController {

    @Autowired
    private ProfileHistoryService profileHistoryService;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // 1. Get history for a specific profile
    @GetMapping("/{profileId}")
    public List<ProfileHistoryResponse> getHistoryByProfileId(@PathVariable Long profileId) {
        return profileHistoryService.getHistoryByProfileId(profileId);
    }

    // 2. Get history by user ID
    @GetMapping("/user/{userId}")
    public List<ProfileHistoryResponse> getHistoryByUserId(@PathVariable Long userId) {
        return profileHistoryService.getHistoryByUserId(userId);
    }

    @GetMapping("/profile/{profileId}/range")
    public ResponseEntity<List<ProfileHistoryResponse>> getHistoryByProfileIdAndDateRange(
            @PathVariable Long profileId,
            @RequestParam("startDate") String startDateStr,
            @RequestParam("endDate") String endDateStr) {

        // Call service method to handle the date parsing and data fetching
        List<ProfileHistoryResponse> historyList = profileHistoryService.getHistoryByProfileIdAndDateRange(profileId, startDateStr, endDateStr);
        return ResponseEntity.ok(historyList);
    }


    // 4. Get history for a specific field
    @GetMapping("/profile/{profileId}/field")
    public List<ProfileHistoryResponse> getHistoryByField(@PathVariable Long profileId, @RequestParam String field) {
        return profileHistoryService.getHistoryByProfileIdAndField(profileId, field);
    }

    // 5. Get last change for a profile
    @GetMapping("/profile/{profileId}/last")
    public ProfileHistoryResponse getLastChangeByProfileId(@PathVariable Long profileId) {
        return profileHistoryService.getLastChangeByProfileId(profileId);
    }

    // 6. Get history by change type
    @GetMapping("/profile/{profileId}/change-type")
    public List<ProfileHistoryResponse> getHistoryByChangeType(@PathVariable Long profileId, @RequestParam String changeType) {
        return profileHistoryService.getHistoryByChangeType(profileId, changeType);
    }

    // 7. Get all history
    @GetMapping("/all")
    public List<ProfileHistoryResponse> getAllHistory() {
        return profileHistoryService.getAllHistory();
    }
}