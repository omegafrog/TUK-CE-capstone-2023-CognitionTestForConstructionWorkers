package com.tukorea.cogTest.controller;

import com.tukorea.cogTest.domain.enums.Role;
import com.tukorea.cogTest.dto.SubjectDTO;
import com.tukorea.cogTest.dto.TestResultDTO;
import com.tukorea.cogTest.dto.TestResultForm;
import com.tukorea.cogTest.response.ResponseUtil;
import com.tukorea.cogTest.service.SubjectService;
import com.tukorea.cogTest.service.TestResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/subject")
public class SubjectController {

    private final SubjectService subjectService;
    private final TestResultService testResultService;

    private final SecurityContext securityContext = SecurityContextHolder.getContext();

    @GetMapping("/{id}/test-result")
    public ResponseEntity<Map<String, Object>> lookupSubjectTestResult(@PathVariable Long id) {
        // TODO : 피험자가 요구할 시 자신의 기록만 볼 수 있어야 함.
        // TODO : 관리자가 요구할 시 관리자가 관리하는 피험자의 기록만 볼 수 있어야 함.
        try {
            Authentication authentication = securityContext.getAuthentication();
            if(authentication.getAuthorities().contains(Role.USER)){
                SubjectDTO loggedInSubject = subjectService.findByUsername(authentication.getName());
                if(!Objects.equals(loggedInSubject.getId(), id)){
                    throw new IllegalArgumentException("You can only see your result.");
                }
            }else if(authentication.getAuthorities().contains(Role.ADMIN)){
                SubjectDTO byUsername = subjectService.findByUsername(authentication.getName());
                Long fieldId = byUsername.getField().getId();
                boolean isAdminManagingSubject = false;
                for(SubjectDTO subjectDTO : subjectService.findSubjectInField(fieldId)){
                    if(subjectDTO.getId().equals(id)){
                        isAdminManagingSubject = true;
                        break;
                    }
                }
                if(!isAdminManagingSubject){
                   throw new IllegalArgumentException("You can only access a subject that you are managing");
                }
            }else{
                throw new RuntimeException("Internal server error");
            }
            List<TestResultDTO> testResult = subjectService.findTestResult(id);
            Map<String, Object> result = new ConcurrentHashMap<>();
            result.put("testResults", testResult);
            return new ResponseEntity<>(ResponseUtil.setResponseBody(HttpStatus.OK, "Get subject " + id + "'s result success", result), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.setWrongRequestErrorResponse(e);
        } catch (RuntimeException e) {
            return ResponseUtil.setInternalErrorResponse(e);
        }
    }

    @PostMapping("/{id}/test-result")
    public ResponseEntity<Map<String, Object>> saveSubjectTestResult(@PathVariable Long id, @ModelAttribute TestResultForm testResult) {
        // TODO : 유니티에서 관리자가 로그인하고 그 관리자가 저장하도록
        try {
            SubjectDTO subjectDTO = subjectService.findSubject(id);
            Map<String, Object> result = new ConcurrentHashMap<>();
            result.put("subject", subjectDTO);
            return new ResponseEntity<>(ResponseUtil.setResponseBody(HttpStatus.OK, "Save subject " + id + "'s result success", result), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.setWrongRequestErrorResponse(e);
        } catch (RuntimeException e) {
            return ResponseUtil.setInternalErrorResponse(e);
        }
    }

}
