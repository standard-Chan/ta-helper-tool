package com.teachAssistantHelper.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Table(name = "class") // 'class'는 예약어라 명시 필요
public class ClassEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Academy academy;

    @ManyToOne(fetch = FetchType.LAZY)
    private ClassType classType;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Student> student;

    @Enumerated(EnumType.STRING)
    private DayOfWeek days;

    private LocalTime startTime;
    private LocalTime endTime;
}
