package com.tukorea.cogTest.controller;

import com.tukorea.cogTest.domain.Subject;
import com.tukorea.cogTest.dto.SubjectDTO;
import com.tukorea.cogTest.dto.SubjectForm;
import com.tukorea.cogTest.response.ResponseUtil;
import com.tukorea.cogTest.service.AdminService;
import com.tukorea.cogTest.service.SubjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.tukorea.cogTest.response.ResponseUtil.setResponseBody;

@RestController
@Slf4j
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private SubjectService subjectService;

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
            return new ResponseEntity<>(body, HttpStatus.OK);
        } catch (NullPointerException | IOException e) {
            return ResponseUtil.setWrongResponse(e);
        }
    }

    @PostMapping("/subject/{id}")
    public ResponseEntity<Map<String, Object>> updateWorker(
            @PathVariable Long id,
            SubjectForm subjectForm
    ){
        try {
            SubjectDTO updatedSubject = subjectService.update(id, subjectForm);
            Map<String, Object> result = new ConcurrentHashMap<>();
            result.put("subject", updatedSubject);
            Map<String, Object> body = setResponseBody(HttpStatus.OK, "Update subject success", result);
            return new ResponseEntity<>(body, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.setWrongResponse(e);
        }
    }

    @DeleteMapping("/subject/{id}")
    public ResponseEntity<Map<String ,Object>> deleteWorker(
            @PathVariable Long id
    ){
        try {
            subjectService.delete(id);
            return new ResponseEntity<>(ResponseUtil.setResponseBody(HttpStatus.OK, "Delete subject success", null), HttpStatus.OK);
        }catch (RuntimeException e){
            return ResponseUtil.setWrongResponse(e);
        }
    }
}
