package com.teachAssistantHelper.dto.classDto;

import com.teachAssistantHelper.domain.ClassEntity;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
public class ClassResponseDto {
    private final Long id;
    private final Long academyId;
    private final String academyName;
    private final Long classTypeId;
    private final String classTypeName;
    private final DayOfWeek days;
    private final LocalTime startTime;
    private final LocalTime endTime;

    public ClassResponseDto(ClassEntity entity) {
        this.id = entity.getId();
        this.academyId = entity.getAcademy().getId();
        this.academyName = entity.getAcademy().getName();
        this.classTypeId = entity.getClassType().getId();
        this.classTypeName = entity.getClassType().getName();
        this.days = entity.getDays();
        this.startTime = entity.getStartTime();
        this.endTime = entity.getEndTime();
    }
}

