package com.teachAssistantHelper.controller;

import com.teachAssistantHelper.dto.notice.NoticeDetailResponseDto;
import com.teachAssistantHelper.dto.notice.NoticeRequestDto;
import com.teachAssistantHelper.dto.notice.NoticeResponseDto;
import com.teachAssistantHelper.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notices")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @PostMapping
    public ResponseEntity<NoticeResponseDto> create(@RequestBody NoticeRequestDto dto) {
        return ResponseEntity.ok(noticeService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<NoticeResponseDto>> getAll() {
        return ResponseEntity.ok(noticeService.getAll());
    }

    @GetMapping("/details")
    public ResponseEntity<List<NoticeDetailResponseDto>> getAllWithDetails() {
        return ResponseEntity.ok(noticeService.getAllWithDetails());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoticeResponseDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(noticeService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoticeResponseDto> update(
            @PathVariable("id") Long id,
            @RequestBody NoticeRequestDto dto
    ) {
        return ResponseEntity.ok(noticeService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        noticeService.delete(id);
        return ResponseEntity.ok("삭제 완료");
    }
}

