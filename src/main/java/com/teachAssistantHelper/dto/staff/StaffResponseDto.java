package com.teachAssistantHelper.dto.staff;

import com.teachAssistantHelper.domain.Staff;
import com.teachAssistantHelper.enumerate.Role;
import lombok.Getter;

@Getter
public class StaffResponseDto {
    private final Long id;
    private final String name;
    private final String userId;
    private final String phoneNumber;
    private final Role role;

    public StaffResponseDto(Staff staff) {
        this.id = staff.getId();
        this.name = staff.getName();
        this.userId = staff.getUserId();
        this.phoneNumber = staff.getPhoneNumber();
        this.role = staff.getRole();
    }
}
