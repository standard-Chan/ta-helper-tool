package com.teachAssistantHelper.service;

import com.teachAssistantHelper.domain.ClassType;
import com.teachAssistantHelper.dto.classType.ClassTypeRequestDto;
import com.teachAssistantHelper.dto.classType.ClassTypeResponseDto;
import com.teachAssistantHelper.dto.classType.ClassTypeUpdateDto;
import com.teachAssistantHelper.exception.domainException.ClassTypeException;
import com.teachAssistantHelper.exception.ErrorCode;
import com.teachAssistantHelper.repository.ClassTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClassTypeService {

    private final ClassTypeRepository classTypeRepository;

    public ClassTypeResponseDto createClassType(ClassTypeRequestDto dto) {
        ClassType classType = ClassType.builder()
                .name(dto.getName())
                .book(dto.getBook())
                .test(dto.getTest())
                .homework(dto.getHomework())
                .build();

        return new ClassTypeResponseDto(classTypeRepository.save(classType));
    }

    public List<ClassTypeResponseDto> getAllClassTypes() {
        return classTypeRepository.findAll().stream()
                .map(ClassTypeResponseDto::new)
                .collect(Collectors.toList());
    }

    public ClassTypeResponseDto updateClassType(Long id, ClassTypeUpdateDto dto) {
        ClassType classType = classTypeRepository.findById(id)
                .orElseThrow(() -> new ClassTypeException(ErrorCode.CLASS_TYPE_NOT_FOUND));

        classType = ClassType.builder()
                .id(classType.getId())
                .name(dto.getName())
                .book(dto.getBook())
                .test(dto.getTest())
                .homework(dto.getHomework())
                .build();

        return new ClassTypeResponseDto(classTypeRepository.save(classType));
    }

    public void deleteClassType(Long id) {
        if (!classTypeRepository.existsById(id)) {
            throw new ClassTypeException(ErrorCode.CLASS_TYPE_NOT_FOUND);
        }
        classTypeRepository.deleteById(id);
    }

}
