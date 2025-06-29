package com.teachAssistantHelper.security;

import com.teachAssistantHelper.domain.Staff;
import com.teachAssistantHelper.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final StaffRepository staffRepository;

    // username으로 사용자 정보를 로드하고 UserDetails 리턴
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Staff staff = staffRepository.findStaffByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + userId));

        return new CustomUserDetails(staff);
    }
}

