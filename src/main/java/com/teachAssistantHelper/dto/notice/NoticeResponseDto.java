package com.teachAssistantHelper.dto.notice;

import com.teachAssistantHelper.domain.Notice;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class NoticeResponseDto {
    private final Long id;
    private final Long classId;
    private final String content;
    private final Timestamp createdAt;
    private final int weekNo;

    public NoticeResponseDto(Notice notice) {
        this.id = notice.getId();
        this.classId = notice.getClassEntity().getId();
        this.content = notice.getContent();
        this.createdAt = notice.getCreatedAt();
        this.weekNo = notice.getWeekNo();
    }
}

