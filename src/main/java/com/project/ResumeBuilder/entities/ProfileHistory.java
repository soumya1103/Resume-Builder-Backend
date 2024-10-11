package com.project.ResumeBuilder.entities;

import com.project.ResumeBuilder.enums.ChangeType;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "profile_history")
public class ProfileHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;

    @Column(name = "profile_id")
    private Long profileId;

    @Column(name = "user_id")
    private Integer userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "change_type")
    private ChangeType changeType;


    @Column(name = "changed_field")
    private String changedField; // Field that was changed, e.g., 'bio', 'profileData'

    @Column(name = "previous_value", columnDefinition = "TEXT")
    private String previousValue; // Previous value before change

    @Column(name = "current_value", columnDefinition = "TEXT")
    private String currentValue; // Current value after change

    @Column(name = "timestamp", columnDefinition = "TIMESTAMP")
    private LocalDateTime timestamp;

    public ProfileHistory(){
        super();
    }

    public ProfileHistory(LocalDateTime timestamp, String currentValue, String previousValue, String changedField, ChangeType changeType, Integer userId, Long profileId) {
        super();
        this.timestamp = timestamp;
        this.currentValue = currentValue;
        this.previousValue = previousValue;
        this.changedField = changedField;
        this.changeType = changeType;
        this.userId = userId;
        this.profileId = profileId;
    }

    // Getters and setters

    public Long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public ChangeType getChangeType() {
        return changeType;
    }

    public void setChangeType(ChangeType changeType) {
        this.changeType = changeType;
    }

    public String getChangedField() {
        return changedField;
    }

    public void setChangedField(String changedField) {
        this.changedField = changedField;
    }

    public String getPreviousValue() {
        return previousValue;
    }

    public void setPreviousValue(String previousValue) {
        this.previousValue = previousValue;
    }

    public String getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfileHistory that = (ProfileHistory) o;
        return Objects.equals(historyId, that.historyId) && Objects.equals(profileId, that.profileId) && Objects.equals(userId, that.userId) && Objects.equals(changeType, that.changeType) && Objects.equals(changedField, that.changedField) && Objects.equals(previousValue, that.previousValue) && Objects.equals(currentValue, that.currentValue) && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(historyId, profileId, userId, changeType, changedField, previousValue, currentValue, timestamp);
    }
}