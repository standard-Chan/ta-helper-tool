package com.teachAssistantHelper.service;

import com.teachAssistantHelper.domain.ClassEntity;
import com.teachAssistantHelper.domain.Student;
import com.teachAssistantHelper.dto.student.StudentRequestDto;
import com.teachAssistantHelper.dto.student.StudentResponseDto;
import com.teachAssistantHelper.exception.domainException.ClassEntityException;
import com.teachAssistantHelper.exception.ErrorCode;
import com.teachAssistantHelper.exception.domainException.StudentException;
import com.teachAssistantHelper.repository.ClassRepository;
import com.teachAssistantHelper.repository.StudentRepository;
import com.teachAssistantHelper.repository.WeeklyClassRecordRepository;
import com.teachAssistantHelper.repository.WeeklyExtraClassRecordRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;
    private final ClassRepository classRepository;
    private final WeeklyClassRecordRepository weeklyClassRecordRepository;
    private final WeeklyExtraClassRecordRepository weeklyExtraClassRecordRepository;

    public StudentResponseDto create(StudentRequestDto dto) {
        ClassEntity classEntity = classRepository.findById(dto.getClassId())
                .orElseThrow(() -> new ClassEntityException(ErrorCode.CLASS_NOT_FOUND));

        Student student = new Student(dto.getName(), dto.getSchool(), dto.getParentPhoneNumber(),
                dto.getPhoneNumber(), dto.getEmail(), dto.getAge());

        student.addClass(classEntity);

        return new StudentResponseDto(studentRepository.save(student));
    }

    public Page<StudentResponseDto> getAll(Pageable pageable) {
        return studentRepository.findAll(pageable)
                .map(StudentResponseDto::new);
    }

    public List<StudentResponseDto> getAllByClass(Long classId) {
        ClassEntity classEntity = classRepository.findById(classId).orElseThrow(() -> new ClassEntityException(ErrorCode.CLASS_NOT_FOUND));

        return studentRepository.getStudentsByClasses(classEntity).stream()
                .map(StudentResponseDto::new).toList();
    }

    public StudentResponseDto getById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentException(ErrorCode.STUDENT_NOT_FOUND));
        return new StudentResponseDto(student);
    }

    public StudentResponseDto update(Long id, StudentRequestDto dto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentException(ErrorCode.STUDENT_NOT_FOUND));
        ClassEntity classEntity = classRepository.findById(dto.getClassId())
                .orElseThrow(() -> new ClassEntityException(ErrorCode.CLASS_NOT_FOUND));

        student.setName(dto.getName());
        student.setSchool(dto.getSchool());
        student.setPhoneNumber(dto.getPhoneNumber());
        student.setParentPhoneNumber(dto.getParentPhoneNumber());
        student.setEmail(dto.getEmail());
        student.setAge(dto.getAge());
        student.addClass(classEntity);

        // class Entity에도 추가하기
        classEntity.getStudent().add(student);

        return new StudentResponseDto(studentRepository.save(student));
    }


    public void delete(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentException(ErrorCode.STUDENT_NOT_FOUND));

        // Class 연관 관계 제거
        for (ClassEntity classEntity : student.getClasses()) {
            classEntity.getStudent().remove(student);
        }
        // WeeklyClassRecord 연관 관계 제거
        weeklyClassRecordRepository.deleteByStudentId(id);
        // WeeklyExtraClassRecord 연관 관계 제거
        weeklyExtraClassRecordRepository.deleteByStudentId(id);

        studentRepository.deleteById(id);
    }
}

