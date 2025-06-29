package com.teachAssistantHelper.domain;

import com.teachAssistantHelper.enumerate.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String userId;
    private String password;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

    public boolean canCreateAccount() {
        return Role.ADMIN.equals(this.role);
    }
}

