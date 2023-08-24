package com.tukorea.cogTest.repository;

import com.tukorea.cogTest.domain.NotPassedCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotPassedCountRepository extends JpaRepository<NotPassedCount, Long> {

    Optional<NotPassedCount> findByField_id(Long fieldId);
}
