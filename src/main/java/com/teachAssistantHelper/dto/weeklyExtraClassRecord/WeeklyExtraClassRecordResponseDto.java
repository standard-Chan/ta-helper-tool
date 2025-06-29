package com.teachAssistantHelper.dto.weeklyExtraClassRecord;

import com.teachAssistantHelper.domain.WeeklyExtraClassRecord;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalTime;

@Getter
public class WeeklyExtraClassRecordResponseDto {
    private final Long id;
    private final Long studentId;
    private final Long extraClassId;
    private final int weekNo;
    private final String reason;
    private final boolean attended;
    private final LocalTime attendedTime;
    private final LocalTime exitTime;
    private final int testScore;
    private final Long createdById;
    private final Long updatedById;
    private final Timestamp createdAt;
    private final Timestamp updatedAt;

    public WeeklyExtraClassRecordResponseDto(WeeklyExtraClassRecord record) {
        this.id = record.getId();
        this.studentId = record.getStudent().getId();
        this.extraClassId = record.getExtraClass().getId();
        this.weekNo = record.getWeekNo();
        this.reason = record.getReason();
        this.attended = record.isAttended();
        this.attendedTime = record.getAttendedTime();
        this.exitTime = record.getExitTime();
        this.testScore = record.getTestScore();
        this.createdById = record.getCreatedBy().getId();
        this.updatedById = record.getUpdatedBy().getId();
        this.createdAt = record.getCreatedAt();
        this.updatedAt = record.getUpdatedAt();
    }
}

