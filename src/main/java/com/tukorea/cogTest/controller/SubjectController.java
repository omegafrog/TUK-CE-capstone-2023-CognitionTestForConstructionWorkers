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

    private final SubjectService subjectService;
    private final TestResultService testResultService;

    @GetMapping("/{id}/test-result")
    public List<TestResult> lookupSubjectTestResult(@PathVariable Long id) {
        return subjectService.findTestResult(id);
    }

    @PostMapping("/{id}/test-result")
    public TestResult saveSubjectTestResult(@PathVariable Long id, @ModelAttribute TestResult testResult){
        Subject subject = subjectService.findSubject(id);
        testResult.assignTarget(subject);
        return testResultService.save(testResult);

    }

}
