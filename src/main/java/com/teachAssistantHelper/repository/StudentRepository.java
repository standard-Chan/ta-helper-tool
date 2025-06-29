package com.teachAssistantHelper.repository;

import com.teachAssistantHelper.domain.ClassEntity;
import com.teachAssistantHelper.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT s FROM Student s JOIN FETCH s.classes c WHERE :classEntity MEMBER OF s.classes ORDER BY s.name")
    List<Student> getStudentsByClasses(@Param("classEntity")ClassEntity classEntity);
}

