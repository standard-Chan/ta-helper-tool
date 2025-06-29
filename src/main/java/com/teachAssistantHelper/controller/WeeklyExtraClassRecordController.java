package com.teachAssistantHelper.controller;

import com.teachAssistantHelper.dto.weeklyExtraClassRecord.WeeklyExtraClassRecordRequestDto;
import com.teachAssistantHelper.dto.weeklyExtraClassRecord.WeeklyExtraClassRecordResponseDto;
import com.teachAssistantHelper.dto.weeklyExtraClassRecord.WeeklyExtraClassRecordWithStudentResponseDto;
import com.teachAssistantHelper.security.CustomUserDetails;
import com.teachAssistantHelper.service.WeeklyExtraClassRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weekly-extra-records")
@RequiredArgsConstructor
public class WeeklyExtraClassRecordController {

    private final WeeklyExtraClassRecordService recordService;

//    @PostMapping("/each")
//    데이터 각각을 확인하여 upsert 하는 방식
//    public ResponseEntity<?> createEach(@RequestBody List<WeeklyExtraClassRecordRequestDto> recordDtoList,
//                                                                    @AuthenticationPrincipal CustomUserDetails userDetails) {
//        List<WeeklyExtraClassRecordResponseDto> response = recordDtoList.stream().map((recordDto) -> recordService.upsert(recordDto, userDetails.getStaff())).toList();
//        return ResponseEntity.ok(response);
//    }

    @PostMapping
    public ResponseEntity<?> upsert(@RequestBody List<WeeklyExtraClassRecordRequestDto> recordDtoList,
                                    @AuthenticationPrincipal CustomUserDetails userDetails) {
        recordService.bulkUpsertExtraRecords(recordDtoList, userDetails.getStaff());
        return ResponseEntity.status(201).build();
    }

    @GetMapping
    public ResponseEntity<List<WeeklyExtraClassRecordWithStudentResponseDto>> getWithStudentByWeekAndExtraClass(@RequestParam("extraClass")Long extraClass,
                                                                                                                @RequestParam("week")int weekNo) {
        return ResponseEntity.ok(recordService.getWithStudentByWeekAndExtraClass(extraClass, weekNo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WeeklyExtraClassRecordResponseDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(recordService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WeeklyExtraClassRecordResponseDto> update(@PathVariable("id") Long id,
                                                                    @RequestBody WeeklyExtraClassRecordRequestDto dto,
                                                                    @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(recordService.update(id, dto, userDetails.getStaff()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        recordService.delete(id);
        return ResponseEntity.ok("삭제 완료");
    }

    @GetMapping("/extraClass/{extraClassId}/week")
    public ResponseEntity<List<Integer>> getWeekNoListByExtraClass (@PathVariable("extraClassId") Long extraClassId) {
        return ResponseEntity.ok(recordService.getWeekNoByExtraClass(extraClassId));
    }

}

