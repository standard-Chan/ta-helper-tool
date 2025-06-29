package com.teachAssistantHelper.dto.classType;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ClassTypeRequestDto {
    private String name;
    private String book;
    private String test;
    private String homework;
}
