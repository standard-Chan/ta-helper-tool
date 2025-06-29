package com.teachAssistantHelper.repository;

import com.teachAssistantHelper.domain.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    Optional<Staff> findStaffByUserId(String userId);
}

