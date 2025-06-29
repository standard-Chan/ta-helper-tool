package com.teachAssistantHelper.dto.academy;

import com.teachAssistantHelper.domain.Academy;
import lombok.Getter;

@Getter
public class AcademyResponseDto {
    private final Long id;
    private final String name;
    private final String address;
    private final String tel;

    public AcademyResponseDto(Academy academy) {
        this.id = academy.getId();
        this.name = academy.getName();
        this.address = academy.getAddress();
        this.tel = academy.getTel();
    }
}

