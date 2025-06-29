package com.teachAssistantHelper.repository;

import com.teachAssistantHelper.domain.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRepository extends JpaRepository<ClassEntity, Long> {
}

