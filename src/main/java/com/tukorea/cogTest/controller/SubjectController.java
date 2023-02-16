package com.tukorea.cogTest.controller;

import com.tukorea.cogTest.domain.Subject;
import com.tukorea.cogTest.domain.TestResult;
import com.tukorea.cogTest.dto.SubjectDTO;
import com.tukorea.cogTest.dto.TestResultDTO;
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
    public List<TestResultDTO> lookupSubjectTestResult(@PathVariable Long id) {
        return subjectService.findTestResult(id);
    }

    @PostMapping("/{id}/test-result")
    public TestResultDTO saveSubjectTestResult(@PathVariable Long id, @ModelAttribute TestResultForm testResult){
        SubjectDTO subjectDTO = subjectService.findSubject(id).toDTO();
        return testResultService.save(testResult, subjectDTO);
    }

}
