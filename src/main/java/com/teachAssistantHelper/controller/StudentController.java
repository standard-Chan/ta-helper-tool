package com.teachAssistantHelper.controller;

import com.teachAssistantHelper.dto.student.StudentRequestDto;
import com.teachAssistantHelper.dto.student.StudentResponseDto;
import com.teachAssistantHelper.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<StudentResponseDto> create(@RequestBody StudentRequestDto dto) {
        return ResponseEntity.ok(studentService.create(dto));
    }

    @GetMapping()
    public ResponseEntity<Page<StudentResponseDto>> getPaged(@RequestParam(name = "page", defaultValue = "0") int page,
                                                             @RequestParam(name = "size", defaultValue = "100") int size) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size);
        return ResponseEntity.ok(studentService.getAll(pageable));
    }

    @GetMapping("/class/{classId}")
    public ResponseEntity<List<StudentResponseDto>> getByClass(@PathVariable("classId") Long classId) {
        return ResponseEntity.ok(studentService.getAllByClass(classId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(studentService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDto> update(
            @PathVariable("id") Long id,
            @RequestBody StudentRequestDto dto
    ) {
        return ResponseEntity.ok(studentService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        studentService.delete(id);
        return ResponseEntity.ok().build();
    }
}

