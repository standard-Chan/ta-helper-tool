package com.teachAssistantHelper.service;

import com.teachAssistantHelper.domain.ClassEntity;
import com.teachAssistantHelper.domain.Staff;
import com.teachAssistantHelper.domain.Student;
import com.teachAssistantHelper.domain.WeeklyClassRecord;
import com.teachAssistantHelper.dto.weeklyClassRecord.WeeklyClassRecordRequestDto;
import com.teachAssistantHelper.dto.weeklyClassRecord.WeeklyClassRecordResponseDto;
import com.teachAssistantHelper.dto.weeklyClassRecord.WeeklyClassRecordWithStudentResponseDto;
import com.teachAssistantHelper.exception.ErrorCode;
import com.teachAssistantHelper.exception.domainException.ClassEntityException;
import com.teachAssistantHelper.exception.domainException.StudentException;
import com.teachAssistantHelper.exception.domainException.WeeklyClassRecordException;
import com.teachAssistantHelper.repository.ClassRepository;
import com.teachAssistantHelper.repository.StudentRepository;
import com.teachAssistantHelper.repository.WeeklyClassRecordRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WeeklyClassRecordService {

    private final WeeklyClassRecordRepository recordRepository;
    private final StudentRepository studentRepository;
    private final ClassRepository classRepository;
    private final JdbcTemplate jdbcTemplate;

    public WeeklyClassRecordResponseDto create(WeeklyClassRecordRequestDto dto, Staff staff) {
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new StudentException(ErrorCode.STUDENT_NOT_FOUND));
        ClassEntity classEntity = classRepository.findById(dto.getClassId())
                .orElseThrow(() -> new ClassEntityException(ErrorCode.CLASS_NOT_FOUND));

        WeeklyClassRecord record = WeeklyClassRecord.builder()
                .student(student)
                .classEntity(classEntity)
                .weekNo(dto.getWeekNo())
                .attended(dto.isAttended())
                .testScore(dto.getTestScore())
                .homeworkScore(dto.getHomeworkScore())
                .note(dto.getNote())
                .createdBy(staff)
                .updatedBy(staff)
                .build();

        return new WeeklyClassRecordResponseDto(recordRepository.save(record));
    }

    public List<WeeklyClassRecordResponseDto> getAll() {
        return recordRepository.findAll().stream()
                .map(WeeklyClassRecordResponseDto::new)
                .collect(Collectors.toList());
    }

    public WeeklyClassRecordResponseDto getById(Long id) {
        WeeklyClassRecord record = recordRepository.findById(id)
                .orElseThrow(() -> new WeeklyClassRecordException(ErrorCode.WEEKLY_RECORD_NOT_FOUND));
        return new WeeklyClassRecordResponseDto(record);
    }

    public WeeklyClassRecordResponseDto update(Long id, WeeklyClassRecordRequestDto dto, Staff staff) {
        WeeklyClassRecord existing = recordRepository.findById(id)
                .orElseThrow(() -> new WeeklyClassRecordException(ErrorCode.WEEKLY_RECORD_NOT_FOUND));
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new StudentException(ErrorCode.STUDENT_NOT_FOUND));
        ClassEntity classEntity = classRepository.findById(dto.getClassId())
                .orElseThrow(() -> new ClassEntityException(ErrorCode.CLASS_NOT_FOUND));

        WeeklyClassRecord updated = WeeklyClassRecord.builder()
                .id(existing.getId())
                .student(student)
                .classEntity(classEntity)
                .weekNo(dto.getWeekNo())
                .attended(dto.isAttended())
                .testScore(dto.getTestScore())
                .homeworkScore(dto.getHomeworkScore())
                .note(dto.getNote())
                .createdBy(existing.getCreatedBy())  // 생성자는 그대로 유지
                .updatedBy(staff)
                .createdAt(existing.getCreatedAt())  // 시간도 유지
                .build();

        return new WeeklyClassRecordResponseDto(recordRepository.save(updated));
    }

    public void delete(Long id) {
        if (!recordRepository.existsById(id)) {
            throw new WeeklyClassRecordException(ErrorCode.WEEKLY_RECORD_NOT_FOUND);
        }
        recordRepository.deleteById(id);
    }

    /**
     * week, class에 맞는 데이터만 검색해서 학생 데이터와 함께 반환
     */
    public List<WeeklyClassRecordWithStudentResponseDto> getWithStudentByWeekAndClass(Long classId, int weekNo) {
        ClassEntity classEntity = classRepository.findById(classId).orElseThrow(() -> new ClassEntityException(ErrorCode.CLASS_NOT_FOUND));

        return recordRepository.getWithStudentWeeklyClassRecordsByClassEntityAndWeekNo(classEntity, weekNo)
                .parallelStream().map(WeeklyClassRecordWithStudentResponseDto::new).toList();
    }

    /**
     * 해당 수업의 weekNo 검색해서 List로 반환
     */
    public List<Integer> getWeekNoListByClass(Long classId) {
        ClassEntity classEntity = classRepository.findById(classId).orElseThrow(() -> new ClassEntityException(ErrorCode.CLASS_NOT_FOUND));

        return recordRepository.findWeekNosByClassEntity(classEntity);
    }

    /**
     * 데이터가 존재하면 UPDATE, 데이터가 없으면 INSERT
     */
    @Transactional
    public WeeklyClassRecordResponseDto upsert(WeeklyClassRecordRequestDto dto, Staff staff) {
        Student student = studentRepository.findById(dto.getStudentId()).orElseThrow(() -> new StudentException(ErrorCode.STUDENT_NOT_FOUND));
        Optional<WeeklyClassRecord> record = recordRepository.findByStudentAndWeekNo(student, dto.getWeekNo());

        // 이미 존재하는 경우
        if (record.isPresent()) { // UPDATE
            return update(record.get().getId(), dto, staff);
        } else { // INSERT
            return create(dto, staff);
        }
    }

    /** upsert Query 를 통한 upsert */
    @Transactional
    public void improvedUpsert(List<WeeklyClassRecordRequestDto> dtos, Staff staff) {

    }

    @Transactional
    public void bulkInsertRecords(List<WeeklyClassRecordRequestDto> dtos, Staff staff) {
        if (dtos.isEmpty()) return;

        String SQL ="INSERT INTO weekly_class_record " +
                "(student_id, class_entity_id, week_no, attended, test_score, homework_score, note, create_by, update_by) " +
                "VALUES (? ,? ,? ,? ,?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(SQL, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                WeeklyClassRecordRequestDto dto = dtos.get(i);
                ps.setLong(1, dto.getStudentId());
                ps.setLong(2, dto.getClassId());
                ps.setInt(3, dto.getWeekNo());
                ps.setBoolean(4, dto.isAttended());
                ps.setInt(5, dto.getTestScore());
                ps.setInt(6, dto.getHomeworkScore());
                ps.setString(7, dto.getNote());
                ps.setLong(8, staff.getId());
                ps.setLong(9, staff.getId());
            }

            @Override
            public int getBatchSize() {
                return dtos.size();
            }
        });
    }

    @Transactional
    public void bulkUpdateRecords(List<WeeklyClassRecordRequestDto> dtos, Staff staff) {
        if (dtos.isEmpty()) return;

        // sql문
        // student_id를 기준으로 하므로, index를 만드는 것이 좋다.
        String SQL = "UPDATE weekly_class_record " +
                "SET test_score = ?, homework_score = ?, attended = ?, note = ?, updated_by = ?" +
                "WHERE student_id = ?";

        jdbcTemplate.batchUpdate(SQL, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                WeeklyClassRecordRequestDto dto = dtos.get(i);

                ps.setInt(1, dto.getTestScore());
                ps.setInt(2, dto.getHomeworkScore());
                ps.setBoolean(3, dto.isAttended());
                ps.setString(4, dto.getNote());
                ps.setLong(5, staff.getId());
                ps.setLong(6, dto.getStudentId());
            }

            @Override
            public int getBatchSize() {
                return dtos.size();
            }
        });
    }

    @Transactional
    public void bulkUpsertRecords(List<WeeklyClassRecordRequestDto> dtos, Staff staff) {
        if (dtos.isEmpty()) return;

        // sql
        // weekly_class_record는 student_id, class_id, week_no 를 기준으로 UNIQUE 제약조건이 걸려있다.
        String sql = """
                        INSERT INTO weekly_class_record
                        (student_id, class_entity_id, week_no, attended, test_score, homework_score, note, created_by, updated_by)
                        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                        ON DUPLICATE KEY UPDATE
                        attended = VALUES(attended),
                        test_score = VALUES(test_score),
                        homework_score = VALUES(homework_score),
                        note = VALUES(note),
                        updated_by = VALUES(updated_by)
                    """;

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                WeeklyClassRecordRequestDto dto = dtos.get(i);

                ps.setLong(1, dto.getStudentId());
                ps.setLong(2, dto.getClassId());
                ps.setInt(3, dto.getWeekNo());
                ps.setBoolean(4, dto.isAttended());
                ps.setInt(5, dto.getTestScore());
                ps.setInt(6, dto.getHomeworkScore());
                ps.setString(7, dto.getNote());
                ps.setLong(8, staff.getId());
                ps.setLong(9, staff.getId());
            }

            public int getBatchSize() {
                return dtos.size();
            }
        });
    }
}



