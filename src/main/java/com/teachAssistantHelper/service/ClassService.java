package com.teachAssistantHelper.service;


import com.teachAssistantHelper.domain.Academy;
import com.teachAssistantHelper.domain.ClassEntity;
import com.teachAssistantHelper.domain.ClassType;
import com.teachAssistantHelper.dto.classDto.ClassRequestDto;
import com.teachAssistantHelper.dto.classDto.ClassResponseDto;
import com.teachAssistantHelper.exception.domainException.ClassEntityException;
import com.teachAssistantHelper.exception.ErrorCode;
import com.teachAssistantHelper.repository.AcademyRepository;
import com.teachAssistantHelper.repository.ClassRepository;
import com.teachAssistantHelper.repository.ClassTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClassService {

    private final ClassRepository classRepository;
    private final AcademyRepository academyRepository;
    private final ClassTypeRepository classTypeRepository;

    /** 생성 */
    public ClassResponseDto create(ClassRequestDto dto) {
        Academy academy = academyRepository.findById(dto.getAcademyId())
                .orElseThrow(() -> new ClassEntityException(ErrorCode.ACADEMY_NOT_FOUND));
        ClassType classType = classTypeRepository.findById(dto.getClassTypeId())
                .orElseThrow(() -> new ClassEntityException(ErrorCode.CLASS_TYPE_NOT_FOUND));

        ClassEntity classEntity = ClassEntity.builder()
                .academy(academy)
                .classType(classType)
                .days(dto.getDays())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .build();

        return new ClassResponseDto(classRepository.save(classEntity));
    }

    public List<ClassResponseDto> getAll() {
        return classRepository.findAll().stream()
                .map(ClassResponseDto::new)
                .collect(Collectors.toList());
    }

    public ClassResponseDto getById(Long id) {
        ClassEntity entity = classRepository.findById(id)
                .orElseThrow(() -> new ClassEntityException(ErrorCode.CLASS_NOT_FOUND));
        return new ClassResponseDto(entity);
    }

    public ClassResponseDto update(Long id, ClassRequestDto dto) {

        Academy academy = academyRepository.findById(dto.getAcademyId())
                .orElseThrow(() -> new ClassEntityException(ErrorCode.ACADEMY_NOT_FOUND));
        ClassType classType = classTypeRepository.findById(dto.getClassTypeId())
                .orElseThrow(() -> new ClassEntityException(ErrorCode.CLASS_TYPE_NOT_FOUND));

        ClassEntity original = classRepository.findById(id)
                .orElseThrow(() -> new ClassEntityException(ErrorCode.CLASS_NOT_FOUND));

        ClassEntity updated = ClassEntity.builder()
                .id(original.getId())
                .academy(academy)
                .classType(classType)
                .days(dto.getDays())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .build();

        return new ClassResponseDto(classRepository.save(updated));
    }

    public void delete(Long id) {
        if (!classRepository.existsById(id)) {
            throw new ClassEntityException(ErrorCode.CLASS_NOT_FOUND);
        }
        classRepository.deleteById(id);
    }
}

