package com.teachAssistantHelper.dto.classType;

import com.teachAssistantHelper.domain.ClassType;
import lombok.Getter;

@Getter
public class ClassTypeResponseDto {
    private final Long id;
    private final String name;
    private final String book;
    private final String test;
    private final String homework;

    public ClassTypeResponseDto(ClassType classType) {
        this.id = classType.getId();
        this.name = classType.getName();
        this.book = classType.getBook();
        this.test = classType.getTest();
        this.homework = classType.getHomework();
    }
}

