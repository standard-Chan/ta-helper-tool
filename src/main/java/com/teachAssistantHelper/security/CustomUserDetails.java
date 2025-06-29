package com.teachAssistantHelper.security;

import com.teachAssistantHelper.domain.Staff;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Getter
public class CustomUserDetails implements UserDetails {

    private final Staff staff;

    public CustomUserDetails(Staff staff) {
        this.staff = staff;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return staff.getPassword(); // 인코딩된 비밀번호
    }

    @Override
    public String getUsername() {
        return staff.getUserId();
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
