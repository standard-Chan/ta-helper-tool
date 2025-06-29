package com.teachAssistantHelper.dto.weeklyClassRecord;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WeeklyClassRecordRequestDto {
    private Long studentId;
    private Long classId;
    private int weekNo;
    private boolean attended;
    private int testScore;
    private int homeworkScore;
    private String note;
}

