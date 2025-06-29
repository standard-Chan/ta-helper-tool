package com.teachAssistantHelper.repository;

import com.teachAssistantHelper.domain.ClassEntity;
import com.teachAssistantHelper.domain.Staff;
import com.teachAssistantHelper.domain.Student;
import com.teachAssistantHelper.domain.WeeklyClassRecord;
import com.teachAssistantHelper.dto.weeklyClassRecord.WeeklyClassRecordRequestDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WeeklyClassRecordRepository extends JpaRepository<WeeklyClassRecord, Long> {

    @Query("SELECT w FROM WeeklyClassRecord w WHERE w.classEntity = :classEntity AND w.weekNo = :weekNo")
    List<WeeklyClassRecord> getWeeklyClassRecordsByClassEntityAndWeekNo(@Param("classEntity") ClassEntity classEntity, @Param("weekNo") int weekNo);

    @Query("SELECT w FROM WeeklyClassRecord w JOIN FETCH w.student WHERE w.classEntity = :classEntity AND w.weekNo = :weekNo")
    List<WeeklyClassRecord> getWithStudentWeeklyClassRecordsByClassEntityAndWeekNo(@Param("classEntity") ClassEntity classEntity, @Param("weekNo") int weekNo);

    @Query("SELECT DISTINCT w.weekNo FROM WeeklyClassRecord w WHERE w.classEntity = :classEntity ORDER BY w.weekNo")
    List<Integer> findWeekNosByClassEntity(@Param("classEntity") ClassEntity classEntity);

    Optional<WeeklyClassRecord> findByStudentAndWeekNo(Student student, int weekNo);

    void deleteByStudentId(Long studentId);
}

