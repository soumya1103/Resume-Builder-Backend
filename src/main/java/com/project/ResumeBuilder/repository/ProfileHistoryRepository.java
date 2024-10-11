package com.project.ResumeBuilder.repository;

import com.project.ResumeBuilder.entities.ProfileHistory;
import com.project.ResumeBuilder.enums.ChangeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProfileHistoryRepository extends JpaRepository<ProfileHistory, Long> {

    List<ProfileHistory> findByProfileId(Long profileId);

    List<ProfileHistory> findByUserId(Long userId);

    List<ProfileHistory> findByProfileIdAndTimestampBetween(Long profileId, LocalDateTime startDate, LocalDateTime endDate);

    List<ProfileHistory> findByProfileIdAndChangedField(Long profileId, String changedField);

    List<ProfileHistory> findByProfileIdAndChangeType(Long profileId, ChangeType changeType);  // Use ChangeType instead of String

    ProfileHistory findFirstByProfileIdOrderByTimestampDesc(Long profileId);
}