package com.teachAssistantHelper.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<ClassEntity> classes;

    private String school;
    private String parentPhoneNumber;
    private String phoneNumber;
    private String email;
    private int age;

    public Student(String name, String school,
                   String parentPhoneNumber, String phoneNumber, String email, int age){
        this.name = name;
        this.school = school;
        this.parentPhoneNumber = parentPhoneNumber;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.age = age;
        this.classes = new HashSet<>();
    }

    public void addClass(ClassEntity classEntity) {
        this.classes.add(classEntity);
    }
}

