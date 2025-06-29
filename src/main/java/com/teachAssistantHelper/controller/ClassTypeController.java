package com.teachAssistantHelper.controller;

import com.teachAssistantHelper.dto.classType.ClassTypeRequestDto;
import com.teachAssistantHelper.dto.classType.ClassTypeResponseDto;
import com.teachAssistantHelper.dto.classType.ClassTypeUpdateDto;
import com.teachAssistantHelper.service.ClassTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/class-types")
@RequiredArgsConstructor
public class ClassTypeController {

    private final ClassTypeService classTypeService;

    @PostMapping
    public ResponseEntity<ClassTypeResponseDto> createClassType(@RequestBody ClassTypeRequestDto dto) {
        return ResponseEntity.ok(classTypeService.createClassType(dto));
    }

    @GetMapping
    public ResponseEntity<List<ClassTypeResponseDto>> getAllClassTypes() {
        return ResponseEntity.ok(classTypeService.getAllClassTypes());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassTypeResponseDto> updateClassType(
            @PathVariable("id") Long id,
            @RequestBody ClassTypeUpdateDto dto
    ) {
        return ResponseEntity.ok(classTypeService.updateClassType(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassType(@PathVariable("id") Long id) {
        classTypeService.deleteClassType(id);
        return ResponseEntity.ok().build();
    }
}
