package com.teachAssistantHelper.dto.notice;

import com.teachAssistantHelper.domain.Notice;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.DayOfWeek;

@Getter
public class NoticeDetailResponseDto {

    private final Long id;
    private final Long classId;
    private final String content;
    private final Timestamp createdAt;
    private final int weekNo;

    private final String academyName;
    private final String classTypeName;
    private final DayOfWeek days;

    public NoticeDetailResponseDto(Notice notice) {
        this.id = notice.getId();
        this.classId = notice.getClassEntity().getId();
        this.content = notice.getContent();
        this.createdAt = notice.getCreatedAt();
        this.weekNo = notice.getWeekNo();

        this.academyName = notice.getClassEntity().getAcademy().getName();
        this.classTypeName = notice.getClassEntity().getClassType().getName();
        this.days = notice.getClassEntity().getDays();
    }
}
