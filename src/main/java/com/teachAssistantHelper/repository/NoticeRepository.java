package com.teachAssistantHelper.repository;

import com.teachAssistantHelper.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    @Query("SELECT n FROM Notice n " +
            "JOIN FETCH n.classEntity c " +
            "JOIN FETCH c.academy " +
            "JOIN FETCH c.classType")
    List<Notice> findAllWithDetails();

}

