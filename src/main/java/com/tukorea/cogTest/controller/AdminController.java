package com.tukorea.cogTest.controller;

import com.tukorea.cogTest.domain.Field;
import com.tukorea.cogTest.domain.Subject;
import com.tukorea.cogTest.dto.AdminDTO;
import com.tukorea.cogTest.dto.SubjectDTO;
import com.tukorea.cogTest.dto.SubjectForm;
import com.tukorea.cogTest.response.ResponseUtil;
import com.tukorea.cogTest.service.AdminService;
import com.tukorea.cogTest.service.SubjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.tukorea.cogTest.response.ResponseUtil.setResponseBody;
import static com.tukorea.cogTest.response.ResponseUtil.setWrongRequestErrorResponse;

@RestController
@Slf4j
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private SubjectService subjectService;


    private final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    /**
     * Admin으로 인증된 유저가 특정 피험자의 정보를 열람한다.
     *
     * @param id : 피험자의 아이디
     * @return {
     *     statusCode : 200,
     *     msg : Get subject success,
     *     results : {
     *         검색한 피험자 객체
     *     }
     * }
     */
    @GetMapping("/subject/{id}")
    public ResponseEntity<Map<String, Object>> getSubject(
            @PathVariable(name = "id") Long id) {
        try {
            SubjectDTO foundedSubject = subjectService.findSubject(id);
            Map<String, Object> result = new ConcurrentHashMap<>();

            result.put("subject", foundedSubject);
            return new ResponseEntity<>(ResponseUtil.setResponseBody(HttpStatus.OK, "Get subject success", result), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(ResponseUtil.setResponseBody(HttpStatus.BAD_REQUEST, "Wrong Requset", null), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 관리자가 관리하는 모든 피험자 정보를 검색한다.
     * @return {
     *     statusCode : 200,
     *     msg : Get subjects success,
     *     results : {
     *         검색한 피험자 객체
     *     }
     * }
     */
    @GetMapping("/subjects")
    public ResponseEntity<Map<String, Object>> getSubjects() {
        String username = (String) authentication.getPrincipal();
        AdminDTO byUsername = adminService.findByUsername(username);

        try {
            Long fieldId = byUsername.getField().getId();
            List<SubjectDTO> subjectList = subjectService.findSubjectInField(fieldId);
            Map<String, Object> result = new ConcurrentHashMap<>();
            result.put("subjects", subjectList);
            return new ResponseEntity<>(ResponseUtil.setResponseBody(HttpStatus.OK, "Get subjects success", result), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.setWrongRequestErrorResponse(e);
        } catch (RuntimeException e) {
            return ResponseUtil.setInternalErrorResponse(e);
        }
    }

    /**
     * 피험자의 개인정보를 추가한다.
     * @param mode "file" : csv 파일로 추가
     *             "multi" : json 형식의 body로 추가
     *             "sole" : form 형식의 값으로 추가
     * @param id : 추가할 현장 id
     * @param file : csv 파일
     * @param subjects : json으로 전달된 피험자 정보
     * @param subject : 피험자 정보
     * @return {
     *     statusCode : 200,
     *     msg : Add subject by &lt;mode&gt; success.
     *     results : {
     *         추가한 피험자 객체
     *     }
     * }
     */
    @PostMapping(value = "/subject")
    public ResponseEntity<Map<String, Object>> addWorker(
            @RequestParam String mode,
            @PathVariable Long id,
            @RequestParam MultipartFile file,
            @RequestParam List<Subject> subjects,
            @ModelAttribute Subject subject
    ) {
        try {
            Map<String, Object> body = switch (mode) {
                case "file" -> adminService.addWorkerByFile(id, file);
                case "multi" -> adminService.addMultiWorkers(id, subjects);
                case "sole" -> adminService.addSoleWorker(id, subject);
                default -> null;
            };
            return new ResponseEntity<>(ResponseUtil.setResponseBody(HttpStatus.OK,"Add subject by "+mode+" success.",
                    body), HttpStatus.OK);
        } catch (NullPointerException | IOException | IllegalArgumentException e) {
            return ResponseUtil.setWrongRequestErrorResponse(e);
        }catch (RuntimeException e){
            return ResponseUtil.setInternalErrorResponse(e);
        }
    }

    /**
     * 피험자의 정보를 수정한다.
     * @param id
     * @param subjectForm
     * @return {
     *     statusCode : 200,
     *     msg : update subject success.
     *     results : {
     *         수정한 피험자 객체
     *     }
     * }
     */
    @PostMapping("/subject/{id}")
    public ResponseEntity<Map<String, Object>> updateWorker(
            @PathVariable Long id,
            SubjectForm subjectForm
    ){
        try {
            AdminDTO byUsername = adminService.findByUsername((String) authentication.getPrincipal());
            Field field = byUsername.getField();

            // 관리자가 관리하는 피험자인지 검사
            List<SubjectDTO> inField = subjectService.findSubjectInField(field.getId());
            long count = inField.stream().filter(subjectDTO -> subjectDTO.getField().equals(field)).count();
            if(count == 0){
                return setWrongRequestErrorResponse(new IllegalArgumentException("해당 피험자의 접근 권한이 없습니다."));
            }

            // 피험자 정보 업데이트
            SubjectDTO updatedSubject = subjectService.update(id, subjectForm);
            Map<String, Object> result = new ConcurrentHashMap<>();
            result.put("subject", updatedSubject);
            Map<String, Object> body = setResponseBody(HttpStatus.OK, "Update subject success", result);
            return new ResponseEntity<>(body, HttpStatus.OK);

        } catch (IllegalArgumentException e){
            return ResponseUtil.setWrongRequestErrorResponse(e);
        }catch (RuntimeException e){
            return ResponseUtil.setInternalErrorResponse(e);
        }
    }

    /**
     * 피험자를 삭제한다. 피험자와 포함된 시험 결과도 삭제한다.
     * @param id 노동자의 id
     * @return
     */

    @DeleteMapping("/subject/{id}")
    public ResponseEntity<Map<String ,Object>> deleteWorker(
            @PathVariable Long id
    ){
        try {
            subjectService.delete(id);
            return new ResponseEntity<>(ResponseUtil.setResponseBody(HttpStatus.OK, "Delete subject success", null), HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return ResponseUtil.setWrongRequestErrorResponse(e);
        }catch (RuntimeException e){
            return ResponseUtil.setInternalErrorResponse(e);
        }
    }
}
