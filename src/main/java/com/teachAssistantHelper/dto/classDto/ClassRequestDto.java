package com.teachAssistantHelper.dto.classDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class ClassRequestDto {
    private Long academyId;
    private Long classTypeId;
    private DayOfWeek days;
    private LocalTime startTime;
    private LocalTime endTime;
}

