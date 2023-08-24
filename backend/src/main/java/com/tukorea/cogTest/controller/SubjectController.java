package com.tukorea.cogTest.controller;

import com.tukorea.cogTest.dto.SubjectDTO;
import com.tukorea.cogTest.dto.TestResultDTO;
import com.tukorea.cogTest.dto.TestResultForm;
import com.tukorea.cogTest.paging.Page;
import com.tukorea.cogTest.response.ResponseUtil;
import com.tukorea.cogTest.service.NotPassedCountService;
import com.tukorea.cogTest.service.SubjectService;
import com.tukorea.cogTest.service.TestResultService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ResponseBody
@RequestMapping("/subject")
@RestController
public class SubjectController {

    @Autowired
    private NotPassedCountService notPassedCountService;

    @Autowired
    private SubjectService subjectService;
    @Autowired
    private TestResultService testResultService;

    @GetMapping("/{id}/test-result")
    public ResponseEntity<Map<String, Object>> lookupSubjectTestResult(
            @PathVariable Long id,
            @RequestParam int curPageNum,
            @RequestParam int contentPerPage
    ) {
        try {
            List<TestResultDTO> testResult = subjectService.findTestResult(id);

            Page page = Page.getPage(curPageNum, contentPerPage, "testResult", testResult);

            Map<String, Object> result = new ConcurrentHashMap<>();
            result.put("page", page);
            return new ResponseEntity<>(ResponseUtil.setResponseBody(HttpStatus.OK, "Get subject " + id + "'s result success", result), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.returnWrongRequestErrorResponse(e);
        } catch (RuntimeException e) {
            return ResponseUtil.setInternalErrorResponse(e);
        }
    }

    @PostMapping("/test-result")
    public ResponseEntity<Map<String, Object>> saveSubjectTestResult(
            @RequestBody TestResultForm testResult) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = (String) authentication.getPrincipal();
            SubjectDTO byUsername = subjectService.findByUsername(username);
            Long id = byUsername.getId();
            TestResultDTO saved = testResultService.save(testResult, id);

            notPassedCountService.updateNotPassedCount(byUsername);

            Map<String, Object> result = new ConcurrentHashMap<>();
            result.put("testResult", saved);
            return new ResponseEntity<>(ResponseUtil.setResponseBody(HttpStatus.OK, "Save subject " + id + "'s result success", result), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.returnWrongRequestErrorResponse(e);
        } catch (RuntimeException e) {
            return ResponseUtil.setInternalErrorResponse(e);
        }
    }
}
