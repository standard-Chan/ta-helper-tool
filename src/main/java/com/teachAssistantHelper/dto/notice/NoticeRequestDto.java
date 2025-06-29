package com.teachAssistantHelper.dto.notice;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticeRequestDto {
    private Long classId;
    private String content;
    private int weekNo;
}

