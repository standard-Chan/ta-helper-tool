package com.teachAssistantHelper.dto.staff;

import com.teachAssistantHelper.enumerate.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StaffRequestDto {
    private String name;
    private String userId;
    private String password;
    private String phoneNumber;
    private Role role;
}
