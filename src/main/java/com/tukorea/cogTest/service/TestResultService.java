package com.tukorea.cogTest.service;

import com.tukorea.cogTest.domain.TestResult;
import com.tukorea.cogTest.domain.TestResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TestResultService {

    private TestResultRepository testResultRepository;

    /**
     * 테스트 결과를 저장한다.
     * @param testResult : 피험자가 보낸 테스트 결과 객체
     * @return TestResult : 저장에 성공하면 저장한 테스트 결과 객체를 반환한다.
     * @throws RuntimeException
     */
    public TestResult save(TestResult testResult)throws RuntimeException{
        return testResultRepository.save(testResult);
    }
}
