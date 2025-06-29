package com.teachAssistantHelper.repository;

import com.teachAssistantHelper.domain.ExtraClass;
import com.teachAssistantHelper.domain.Student;
import com.teachAssistantHelper.domain.WeeklyExtraClassRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;

public interface WeeklyExtraClassRecordRepository extends JpaRepository<WeeklyExtraClassRecord, Long> {

    @Query("SELECT w FROM WeeklyExtraClassRecord w WHERE w.student = :student AND w.weekNo = :weekNo")
    Optional<WeeklyExtraClassRecord> findByStudentAndWeekNo(@Param("student") Student student,@Param("weekNo") int weekNo);

    @Query("SELECT DISTINCT r.weekNo FROM WeeklyExtraClassRecord r WHERE r.extraClass = :extraClass ORDER BY r.weekNo")
    List<Integer> getWeekNoListByExtraClass(@Param("extraClass") ExtraClass extraClass);

    @Query("SELECT w FROM WeeklyExtraClassRecord w JOIN FETCH w.student WHERE w.extraClass = :extraClass AND w.weekNo = :weekNo ORDER BY w.student.name")
    List<WeeklyExtraClassRecord> getWithStudentByExtraClassAndWeekNo(@Param("extraClass") ExtraClass extraClass, @Param("weekNo") int weekNo);

    void deleteByStudentId(Long studentId);
}

