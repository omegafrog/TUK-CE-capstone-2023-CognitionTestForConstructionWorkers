package com.tukorea.cogTest.repository;

import com.tukorea.cogTest.domain.Field;
import com.tukorea.cogTest.domain.NotPassedCount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface JpaNotPassedCountRepository extends JpaRepository<NotPassedCount, Long> {
    List<NotPassedCount> findByField_id(Long fieldId);

}
