package com.project.ResumeBuilder.dtos;

import java.time.LocalDateTime;

public class ProfileHistoryResponse {

    private Long historyId;
    private Long profileId;
    private Integer userId;
    private String changeType;
    private String changedField;
    private Object previousValue;  // Deserialized value
    private Object currentValue;   // Deserialized value
    private LocalDateTime timestamp;

    // Getters and Setters

    public Long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Object getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Object currentValue) {
        this.currentValue = currentValue;
    }

    public String getChangedField() {
        return changedField;
    }

    public void setChangedField(String changedField) {
        this.changedField = changedField;
    }

    public Object getPreviousValue() {
        return previousValue;
    }

    public void setPreviousValue(Object previousValue) {
        this.previousValue = previousValue;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }
}