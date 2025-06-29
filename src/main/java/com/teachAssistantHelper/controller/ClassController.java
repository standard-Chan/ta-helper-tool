package com.teachAssistantHelper.controller;

import com.teachAssistantHelper.dto.classDto.ClassRequestDto;
import com.teachAssistantHelper.dto.classDto.ClassResponseDto;
import com.teachAssistantHelper.service.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
@RequiredArgsConstructor
public class ClassController {

    private final ClassService classService;

    @PostMapping
    public ResponseEntity<ClassResponseDto> create(@RequestBody ClassRequestDto dto) {
        return ResponseEntity.ok(classService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<ClassResponseDto>> getAll() {
        return ResponseEntity.ok(classService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassResponseDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(classService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassResponseDto> update(
            @PathVariable("id") Long id,
            @RequestBody ClassRequestDto dto
    ) {
        return ResponseEntity.ok(classService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        classService.delete(id);
        return ResponseEntity.ok().build();
    }
}

