package com.teachAssistantHelper.dto.extraClassRequest;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class ExtraClassRequestDto {
    private Long academyId;
    private Long staffId;
    private DayOfWeek days;
    private LocalTime startTime;
    private LocalTime endTime;
}
