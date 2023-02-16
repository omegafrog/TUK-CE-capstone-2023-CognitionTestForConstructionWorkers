package com.tukorea.cogTest.controller;

import com.tukorea.cogTest.domain.Subject;
import com.tukorea.cogTest.domain.TestResult;
import com.tukorea.cogTest.dto.TestResultForm;
import com.tukorea.cogTest.service.SubjectService;
import com.tukorea.cogTest.service.TestResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/subject")
public class SubjectController {

    private final TestResultService testResultService;
    private final SubjectService subjectService;

    @GetMapping("/{id}/test-result")
    public List<TestResult> lookupSubjectTestResult(@PathVariable Long id) throws RuntimeException {
        return testResultService.findBySubjectId(id);
    }

    @PostMapping("/{id}/test-result")
    public TestResult saveSubjectTestResult(@PathVariable Long id,  TestResultForm testResult) throws RuntimeException{
        Subject foundedSubject = subjectService.findSubject(id);
        return testResultService.save(testResult, foundedSubject);
    }

}
