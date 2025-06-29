package com.teachAssistantHelper.controller;

import com.teachAssistantHelper.dto.weeklyClassRecord.WeeklyClassRecordRequestDto;
import com.teachAssistantHelper.dto.weeklyClassRecord.WeeklyClassRecordResponseDto;
import com.teachAssistantHelper.dto.weeklyClassRecord.WeeklyClassRecordWithStudentResponseDto;
import com.teachAssistantHelper.security.CustomUserDetails;
import com.teachAssistantHelper.service.WeeklyClassRecordService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weekly-records")
@RequiredArgsConstructor
public class WeeklyClassRecordController {

    private final WeeklyClassRecordService recordService;

//    @PostMapping("/each")
//    개별 레코드 처리 방식
//    public ResponseEntity<?> saveEachRecords(@RequestBody List<WeeklyClassRecordRequestDto> recordDtoList,
//                                                                          @AuthenticationPrincipal CustomUserDetails userDetails) {
//        List<WeeklyClassRecordResponseDto> response = recordDtoList.stream()
//                .map(recordDto ->recordService.upsert(recordDto, userDetails.getStaff()))
//                .toList();
//        return ResponseEntity.status(201).build();
//    }

    @PostMapping
    public ResponseEntity<?> saveRecords(@RequestBody List<WeeklyClassRecordRequestDto> recordDtoList,
                                         @AuthenticationPrincipal CustomUserDetails userDetails) {
        recordService.bulkUpsertRecords(recordDtoList, userDetails.getStaff());
        return ResponseEntity.status(201).build();
    }

//    @GetMapping
//    public ResponseEntity<List<WeeklyClassRecordResponseDto>> getAll() {
//        return ResponseEntity.ok(recordService.getAll());
//    }

    @GetMapping()
    public ResponseEntity<List<WeeklyClassRecordWithStudentResponseDto>> getWithStudentByWeekAndClass(@RequestParam("class") Long classId,
                                                                                                      @RequestParam("week") int weekNo) {
        return ResponseEntity.ok(recordService.getWithStudentByWeekAndClass(classId, weekNo));
    }

    @GetMapping("/class/{classId}/week")
    public ResponseEntity<List<Integer>> getWeekNoListByClass(@PathVariable("classId") Long classId) {
        return ResponseEntity.ok(recordService.getWeekNoListByClass(classId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WeeklyClassRecordResponseDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(recordService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WeeklyClassRecordResponseDto> update(
            @PathVariable("id") Long id,
            @RequestBody WeeklyClassRecordRequestDto dto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.ok(recordService.update(id, dto, userDetails.getStaff()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        recordService.delete(id);
        return ResponseEntity.ok("삭제 완료");
    }
}

