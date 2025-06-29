package com.teachAssistantHelper.dto.extraClassRequest;

import com.teachAssistantHelper.domain.ExtraClass;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
public class ExtraClassResponseDto {
    private final Long id;
    private final Long academyId;
    private final String academyName;
    private final Long staffId;
    private final String staffName;
    private final DayOfWeek days;
    private final LocalTime startTime;
    private final LocalTime endTime;

    public ExtraClassResponseDto(ExtraClass ec) {
        this.id = ec.getId();
        this.academyId = ec.getAcademy().getId();
        this.academyName = ec.getAcademy().getName();
        this.staffId = ec.getStaff().getId();
        this.staffName = ec.getStaff().getName();
        this.days = ec.getDays();
        this.startTime = ec.getStartTime();
        this.endTime = ec.getEndTime();
    }
}

