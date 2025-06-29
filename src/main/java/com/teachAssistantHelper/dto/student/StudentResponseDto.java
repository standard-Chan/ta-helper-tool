package com.teachAssistantHelper.dto.student;

import com.teachAssistantHelper.domain.Student;
import lombok.Getter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class StudentResponseDto {
    private final Long id;
    private final String name;
    private final Set<Long> classId;
    private final String school;
    private final String parentPhoneNumber;
    private final String phoneNumber;
    private final String email;
    private final int age;

    public StudentResponseDto(Student student) {
        this.id = student.getId();
        this.name = student.getName();
        this.classId = student.getClasses().stream()
                .map(classEntity -> classEntity.getId())
                .collect(Collectors.toSet());
        this.school = student.getSchool();
        this.parentPhoneNumber = student.getParentPhoneNumber();
        this.phoneNumber = student.getPhoneNumber();
        this.email = student.getEmail();
        this.age = student.getAge();
    }
}

