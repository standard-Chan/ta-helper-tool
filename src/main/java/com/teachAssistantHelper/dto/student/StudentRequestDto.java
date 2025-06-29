package com.teachAssistantHelper.dto.student;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StudentRequestDto {
    private String name;
    private Long classId;
    private String school;
    private String parentPhoneNumber;
    private String phoneNumber;
    private String email;
    private int age;
}

