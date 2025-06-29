package com.teachAssistantHelper.controller;

import com.teachAssistantHelper.dto.academy.AcademyRequestDto;
import com.teachAssistantHelper.dto.academy.AcademyResponseDto;
import com.teachAssistantHelper.service.AcademyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/academies")
@RequiredArgsConstructor
public class AcademyController {

    private final AcademyService academyService;

    @PostMapping
    public ResponseEntity<AcademyResponseDto> create(@RequestBody AcademyRequestDto dto) {
        return ResponseEntity.ok(academyService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<AcademyResponseDto>> getAll() {
        return ResponseEntity.ok(academyService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AcademyResponseDto> update(
            @PathVariable("id") Long id,
            @RequestBody AcademyRequestDto dto
    ) {
        return ResponseEntity.ok(academyService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        academyService.delete(id);
        return ResponseEntity.ok().build();
    }
}

