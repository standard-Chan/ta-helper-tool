package com.teachAssistantHelper.service;

import com.teachAssistantHelper.domain.Staff;
import com.teachAssistantHelper.dto.staff.StaffRequestDto;
import com.teachAssistantHelper.dto.staff.StaffResponseDto;
import com.teachAssistantHelper.dto.staff.StaffRoleUpdateRequestDto;
import com.teachAssistantHelper.exception.ErrorCode;
import com.teachAssistantHelper.exception.domainException.StaffException;
import com.teachAssistantHelper.repository.StaffRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StaffService {

    private final StaffRepository staffRepository;
    private final PasswordEncoder passwordEncoder;

    public List<StaffResponseDto> getAllStaffs() {
        return staffRepository.findAll().stream()
                .map(StaffResponseDto::new)
                .collect(Collectors.toList());
    }

    public StaffResponseDto getStaffById(Long id) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new StaffException(ErrorCode.STAFF_NOT_FOUND));
        return new StaffResponseDto(staff);
    }

    // 현재는 단순 생성방식으로 구현.
    // 이후 jwt를 통해 ROLE을 확인한 이후 ROLE이 ADMIN인 경우에만 생성 가능하도록 수정할 것
    public StaffResponseDto createStaff(StaffRequestDto dto) {
        Staff staff = Staff.builder()
                .name(dto.getName())
                .userId(dto.getUserId())
                .password(passwordEncoder.encode(dto.getPassword()))  // 암호화
                .phoneNumber(dto.getPhoneNumber())
                .role(dto.getRole())
                .build();
        return new StaffResponseDto(staffRepository.save(staff));
    }

    public void deleteStaff(Long id) {
        if (!staffRepository.existsById(id)) {
            throw new StaffException(ErrorCode.STAFF_NOT_FOUND);
        }
        staffRepository.deleteById(id);
    }

    @Transactional
    // 현재는 ID를 통한 권한 확인 방식.
    // 추후 JWT로 요청자의 권한을 얻어서, 확인하도록 수정할 것
    public StaffResponseDto updateRole(Long id, StaffRoleUpdateRequestDto dto) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new StaffException(ErrorCode.STAFF_NOT_FOUND));

        // Optional: 같은 권한이면 예외 처리
        if (staff.getRole() == dto.getRole()) {
            throw new StaffException(ErrorCode.UNAUTHORIZED_ROLE); // 재사용 가능
        }

        Staff updated = Staff.builder()
                .id(staff.getId())
                .name(staff.getName())
                .userId(staff.getUserId())
                .password(staff.getPassword())
                .phoneNumber(staff.getPhoneNumber())
                .role(dto.getRole())
                .build();

        return new StaffResponseDto(staffRepository.save(updated));
    }
}

