package com.teachAssistantHelper.service;

import com.teachAssistantHelper.domain.ClassEntity;
import com.teachAssistantHelper.domain.Notice;
import com.teachAssistantHelper.dto.notice.NoticeDetailResponseDto;
import com.teachAssistantHelper.dto.notice.NoticeRequestDto;
import com.teachAssistantHelper.dto.notice.NoticeResponseDto;
import com.teachAssistantHelper.exception.domainException.ClassEntityException;
import com.teachAssistantHelper.exception.ErrorCode;
import com.teachAssistantHelper.exception.domainException.NoticeException;
import com.teachAssistantHelper.repository.ClassRepository;
import com.teachAssistantHelper.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final ClassRepository classRepository;

    public NoticeResponseDto create(NoticeRequestDto dto) {
        ClassEntity classEntity = classRepository.findById(dto.getClassId())
                .orElseThrow(() -> new ClassEntityException(ErrorCode.CLASS_NOT_FOUND));

        Notice notice = Notice.builder()
                .classEntity(classEntity)
                .content(dto.getContent())
                .weekNo(dto.getWeekNo())
                .build();

        return new NoticeResponseDto(noticeRepository.save(notice));
    }

    public List<NoticeResponseDto> getAll() {
        return noticeRepository.findAll().stream()
                .map(NoticeResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<NoticeDetailResponseDto> getAllWithDetails() {
        return noticeRepository.findAllWithDetails().stream()
                .map(NoticeDetailResponseDto::new)
                .collect(Collectors.toList());
    }

    public NoticeResponseDto getById(Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new NoticeException(ErrorCode.NOTICE_NOT_FOUND));
        return new NoticeResponseDto(notice);
    }

    public NoticeResponseDto update(Long id, NoticeRequestDto dto) {
        Notice original = noticeRepository.findById(id)
                .orElseThrow(() -> new NoticeException(ErrorCode.NOTICE_NOT_FOUND));

        ClassEntity classEntity = classRepository.findById(dto.getClassId())
                .orElseThrow(() -> new ClassEntityException(ErrorCode.CLASS_NOT_FOUND));

        Notice updated = Notice.builder()
                .id(original.getId())
                .classEntity(classEntity)
                .content(dto.getContent())
                .weekNo(dto.getWeekNo())
                .createdAt(original.getCreatedAt())
                .build();

        return new NoticeResponseDto(noticeRepository.save(updated));
    }

    public void delete(Long id) {
        if (!noticeRepository.existsById(id)) {
            throw new NoticeException(ErrorCode.NOTICE_NOT_FOUND);
        }
        noticeRepository.deleteById(id);
    }
}

