package com.teachAssistantHelper.service;

import com.teachAssistantHelper.domain.Academy;
import com.teachAssistantHelper.dto.academy.AcademyRequestDto;
import com.teachAssistantHelper.dto.academy.AcademyResponseDto;
import com.teachAssistantHelper.exception.domainException.AcademyException;
import com.teachAssistantHelper.exception.ErrorCode;
import com.teachAssistantHelper.repository.AcademyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AcademyService {

    private final AcademyRepository academyRepository;

    public AcademyResponseDto create(AcademyRequestDto dto) {
        Academy academy = Academy.builder()
                .name(dto.getName())
                .address(dto.getAddress())
                .tel(dto.getTel())
                .build();
        return new AcademyResponseDto(academyRepository.save(academy));
    }

    public List<AcademyResponseDto> findAll() {
        return academyRepository.findAll().stream()
                .map(AcademyResponseDto::new)
                .collect(Collectors.toList());
    }

    public AcademyResponseDto update(Long id, AcademyRequestDto dto) {
        Academy existing = academyRepository.findById(id)
                .orElseThrow(() -> new AcademyException(ErrorCode.CLASS_TYPE_NOT_FOUND)); // 필요시 ACADEMY_NOT_FOUND 추가해도 됨

        Academy updated = Academy.builder()
                .id(existing.getId())
                .name(dto.getName())
                .address(dto.getAddress())
                .tel(dto.getTel())
                .build();

        return new AcademyResponseDto(academyRepository.save(updated));
    }

    public void delete(Long id) {
        if (!academyRepository.existsById(id)) {
            throw new AcademyException(ErrorCode.ACADEMY_NOT_FOUND);
        }
        academyRepository.deleteById(id);
    }
}

