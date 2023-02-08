package com.tukorea.cogTest.repository;

import com.tukorea.cogTest.domain.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSimpleTestResultRepository extends JpaRepository<TestResult, Long> {

}
