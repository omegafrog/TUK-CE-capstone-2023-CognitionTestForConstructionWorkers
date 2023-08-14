package com.tukorea.cogTest.service;


import com.tukorea.cogTest.domain.Subject;
import com.tukorea.cogTest.domain.SubjectRepository;
import com.tukorea.cogTest.domain.TestResult;
import com.tukorea.cogTest.domain.TestResultRepository;
import com.tukorea.cogTest.domain.enums.Risk;
import com.tukorea.cogTest.dto.TestResultDTO;
import com.tukorea.cogTest.dto.TestResultForm;

import jakarta.persistence.PersistenceException;
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
    public TestResultDTO save(TestResultForm testResultForm, Long subjectId)
            throws RuntimeException {
        Subject foundedSubject = subjectRepository.findById(subjectId);
        TestResult testResult = TestResult.builder()
                .target(foundedSubject)
                .twoHandResult(testResultForm.getTwoHandResult())
                .conveyorResult(testResultForm.getConveyorResult())
                .mazeResult(testResultForm.getMazeResult())
                .decisionMakingResult(testResultForm.getDecisionMakingResult())
                .digitSpanResult(testResultForm.getDigitSpanResult())
                .build();
        Integer testPassedNum = 0;

        if(testResult.getTwoHandResult().getIsPassed()) testPassedNum++;
        if(testResult.getConveyorResult().getIsPassed()) testPassedNum++;
        if(testResult.getMazeResult().getIsPassed()) testPassedNum++;
        if(testResult.getDecisionMakingResult().getIsPassed()) testPassedNum++;
        if(testResult.getDigitSpanResult().getIsPassed()) testPassedNum++;

        if(testPassedNum==0) foundedSubject.changeRiskLevel(Risk.HIGH_RISK);
        if(0<testPassedNum && testPassedNum<3) foundedSubject.changeRiskLevel(Risk.MIDEUM_RISK);
        if(3<=testPassedNum && testPassedNum<5) foundedSubject.changeRiskLevel(Risk.LOW_RISK);
        if(testPassedNum == 5) foundedSubject.changeRiskLevel(Risk.NORMAL);
        foundedSubject.setLastTestedDate(testResult.getDate());

        subjectRepository.save(foundedSubject);
        return testResultRepository.save(testResult).toDTO();
    }

    public List<TestResult> findBySubjectId(Long id) throws RuntimeException{
        return testResultRepository.findByUserId(id);
    }
}
