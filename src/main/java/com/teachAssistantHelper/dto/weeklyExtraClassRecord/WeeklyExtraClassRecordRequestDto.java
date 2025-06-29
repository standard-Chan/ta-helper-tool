package com.teachAssistantHelper.dto.weeklyExtraClassRecord;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class WeeklyExtraClassRecordRequestDto {
    private Long studentId;
    private Long extraClassId;
    private int weekNo;
    private String reason;
    private boolean attended;
    private LocalTime attendedTime;
    private LocalTime exitTime;
    private int testScore;
}
