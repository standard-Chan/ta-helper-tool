package com.teachAssistantHelper.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ExtraClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Academy academy;

    @Enumerated(EnumType.STRING)
    private DayOfWeek days;

    private LocalTime startTime;
    private LocalTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private Staff staff;
}
