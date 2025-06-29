package com.teachAssistantHelper.dto.weeklyClassRecord;

import com.teachAssistantHelper.domain.WeeklyClassRecord;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class WeeklyClassRecordResponseDto {
    private final Long id;
    private final Long studentId;
    private final Long classId;
    private final int weekNo;
    private final boolean attended;
    private final int testScore;
    private final int homeworkScore;
    private final String note;
    private final Long createdById;
    private final Long updatedById;
    private final Timestamp createdAt;
    private final Timestamp updatedAt;

    public WeeklyClassRecordResponseDto(WeeklyClassRecord record) {
        this.id = record.getId();
        this.studentId = record.getStudent().getId();
        this.classId = record.getClassEntity().getId();
        this.weekNo = record.getWeekNo();
        this.attended = record.isAttended();
        this.testScore = record.getTestScore();
        this.homeworkScore = record.getHomeworkScore();
        this.note = record.getNote();
        this.createdById = record.getCreatedBy().getId();
        this.updatedById = record.getUpdatedBy().getId();
        this.createdAt = record.getCreatedAt();
        this.updatedAt = record.getUpdatedAt();
    }
}
