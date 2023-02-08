package com.tukorea.cogTest.domain;


import java.util.Optional;

public interface TestResultRepository {

    TestResult save(TestResult item);

    TestResult update(Long id, TestResult item);

    TestResult findById(Long id);
}
