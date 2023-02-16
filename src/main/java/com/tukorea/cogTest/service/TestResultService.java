package com.tukorea.cogTest.service;


import com.tukorea.cogTest.domain.Subject;
import com.tukorea.cogTest.domain.TestResult;
import com.tukorea.cogTest.domain.TestResultRepository;
import com.tukorea.cogTest.dto.SubjectDTO;
import com.tukorea.cogTest.dto.TestResultDTO;
import com.tukorea.cogTest.dto.TestResultForm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TestResultService {

    private TestResultRepository testResultRepository;

    /**
     * 테스트 결과를 저장한다.

     * @param testResultForm 테스트 결과 폼 dto
     * @param subjectDTO 피험자 DTO 객체
     * @return TestResult 저장한 테스트 결과 객체
     * @throws IllegalArgumentException 테스트 결과 객체의 target이 유효하지 않을 때 발생
     */
    public TestResultDTO save(TestResultForm testResultForm, SubjectDTO subjectDTO)throws IllegalArgumentException{
        TestResult testResult = TestResult.builder()
                .date(testResultForm.getDate())
                .pvtResult(testResultForm.getPvtResult())
                .twohandResult(testResultForm.getTwohandResult())
                .build();
        testResultForm.setTarget(subjectDTO);
        return testResultRepository.save(testResult).toDTO();
    }

    public List<TestResult> findBySubjectId(Long id){
        return testResultRepository.findByUserId(id);
    }


}
