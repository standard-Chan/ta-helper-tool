package com.teachAssistantHelper.service;

import com.teachAssistantHelper.domain.Academy;
import com.teachAssistantHelper.domain.ExtraClass;
import com.teachAssistantHelper.domain.Staff;
import com.teachAssistantHelper.dto.extraClassRequest.ExtraClassRequestDto;
import com.teachAssistantHelper.dto.extraClassRequest.ExtraClassResponseDto;
import com.teachAssistantHelper.exception.ErrorCode;
import com.teachAssistantHelper.exception.domainException.AcademyException;
import com.teachAssistantHelper.exception.domainException.ExtraClassException;
import com.teachAssistantHelper.exception.domainException.StaffException;
import com.teachAssistantHelper.repository.AcademyRepository;
import com.teachAssistantHelper.repository.ExtraClassRepository;
import com.teachAssistantHelper.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExtraClassService {

    private final ExtraClassRepository extraClassRepository;
    private final AcademyRepository academyRepository;
    private final StaffRepository staffRepository;

    public ExtraClassResponseDto create(ExtraClassRequestDto dto) {
        Academy academy = academyRepository.findById(dto.getAcademyId())
                .orElseThrow(() -> new AcademyException(ErrorCode.ACADEMY_NOT_FOUND));
        Staff staff = staffRepository.findById(dto.getStaffId())
                .orElseThrow(() -> new StaffException(ErrorCode.STAFF_NOT_FOUND));

        ExtraClass ec = ExtraClass.builder()
                .academy(academy)
                .staff(staff)
                .days(dto.getDays())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .build();

        return new ExtraClassResponseDto(extraClassRepository.save(ec));
    }

    public List<ExtraClassResponseDto> getAll() {
        return extraClassRepository.findAll().stream()
                .map(ExtraClassResponseDto::new)
                .collect(Collectors.toList());
    }

    public ExtraClassResponseDto getById(Long id) {
        ExtraClass ec = extraClassRepository.findById(id)
                .orElseThrow(() -> new ExtraClassException(ErrorCode.EXTRA_CLASS_NOT_FOUND));
        return new ExtraClassResponseDto(ec);
    }

    public ExtraClassResponseDto update(Long id, ExtraClassRequestDto dto) {
        ExtraClass original = extraClassRepository.findById(id)
                .orElseThrow(() -> new ExtraClassException(ErrorCode.EXTRA_CLASS_NOT_FOUND));

        Academy academy = academyRepository.findById(dto.getAcademyId())
                .orElseThrow(() -> new AcademyException(ErrorCode.ACADEMY_NOT_FOUND));
        Staff staff = staffRepository.findById(dto.getStaffId())
                .orElseThrow(() -> new StaffException(ErrorCode.STAFF_NOT_FOUND));

        ExtraClass updated = ExtraClass.builder()
                .id(original.getId())
                .academy(academy)
                .staff(staff)
                .days(dto.getDays())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .build();

        return new ExtraClassResponseDto(extraClassRepository.save(updated));
    }

    public void delete(Long id) {
        if (!extraClassRepository.existsById(id)) {
            throw new ExtraClassException(ErrorCode.EXTRA_CLASS_NOT_FOUND);
        }
        extraClassRepository.deleteById(id);
    }
}

