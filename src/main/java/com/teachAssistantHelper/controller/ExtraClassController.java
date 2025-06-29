package com.teachAssistantHelper.controller;

import com.teachAssistantHelper.dto.extraClassRequest.ExtraClassRequestDto;
import com.teachAssistantHelper.dto.extraClassRequest.ExtraClassResponseDto;
import com.teachAssistantHelper.service.ExtraClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/extra-classes")
@RequiredArgsConstructor
public class ExtraClassController {

    private final ExtraClassService extraClassService;

    @PostMapping
    public ResponseEntity<ExtraClassResponseDto> create(@RequestBody ExtraClassRequestDto dto) {
        return ResponseEntity.ok(extraClassService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<ExtraClassResponseDto>> getAll() {
        return ResponseEntity.ok(extraClassService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExtraClassResponseDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(extraClassService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExtraClassResponseDto> update(@PathVariable("id") Long id,
                                                        @RequestBody ExtraClassRequestDto dto) {
        return ResponseEntity.ok(extraClassService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        extraClassService.delete(id);
        return ResponseEntity.ok("삭제 완료");
    }
}

