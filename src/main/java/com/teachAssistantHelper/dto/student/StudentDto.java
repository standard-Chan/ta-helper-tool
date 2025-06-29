package com.teachAssistantHelper.dto.student;

import com.teachAssistantHelper.domain.Student;
import lombok.Getter;

@Getter
public class StudentDto {
    private final Long id;
    private final String name;
    private final String school;
    private final String parentPhoneNumber;
    private final String phoneNumber;
    private final String email;
    private final int age;

    public StudentDto(Student student) {
        this.id = student.getId();
        this.name = student.getName();
        this.school = student.getSchool();
        this.parentPhoneNumber = student.getParentPhoneNumber();
        this.phoneNumber = student.getPhoneNumber();
        this.email = student.getEmail();
        this.age = student.getAge();
    }
}