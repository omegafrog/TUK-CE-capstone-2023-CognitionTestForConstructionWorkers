package com.tukorea.cogTest.service;


import com.tukorea.cogTest.domain.Subject;
import com.tukorea.cogTest.domain.SubjectRepository;
import com.tukorea.cogTest.domain.TestResult;
import com.tukorea.cogTest.domain.TestResultRepository;
import com.tukorea.cogTest.dto.TestResultDTO;
import com.tukorea.cogTest.dto.TestResultForm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional
@RequiredArgsConstructor
public class TestResultService {

    private final TestResultRepository testResultRepository;

    private final SubjectRepository subjectRepository;

    /**
     * 테스트 결과를 저장한다.
     *
     * @param testResultForm 테스트 결과 폼 dto
     * @return TestResult 저장한 테스트 결과 객체
     * @throws IllegalArgumentException 테스트 결과 객체의 target이 유효하지 않을 때 발생
     */
    public TestResultDTO save(TestResultForm testResultForm, Long subjectId) throws IllegalArgumentException {
        Subject foundedSubject = subjectRepository.findById(subjectId);
        TestResult testResult = TestResult.builder()
                .target(foundedSubject)
                .twoHandResult(testResultForm.getTwoHandResult())
                .conveyorResult(testResultForm.getConveyorResult())
                .mazeResult(testResultForm.getMazeResult())
                .decisionMakingResult(testResultForm.getDecisionMakingResult())
                .digitSpanResult(testResultForm.getDigitSpanResult())
                .build();

        return testResultRepository.save(testResult).toDTO();
    }

    public List<TestResult> findBySubjectId(Long id){
        return testResultRepository.findByUserId(id);
    }
}
