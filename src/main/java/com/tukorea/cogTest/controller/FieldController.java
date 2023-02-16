package com.tukorea.cogTest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tukorea.cogTest.domain.Subject;
import com.tukorea.cogTest.domain.enums.DetailedJob;
import com.tukorea.cogTest.domain.enums.Risk;
import com.tukorea.cogTest.dto.FieldDTO;
import com.tukorea.cogTest.dto.SubjectForm;
import com.tukorea.cogTest.dto.FieldForm;
import com.tukorea.cogTest.service.FieldService;
import com.tukorea.cogTest.service.SubjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@Slf4j
@RequestMapping("/site")
public class FieldController {

    @Autowired
    private FieldService fieldService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("")
    public ResponseEntity<Map<String, Object>> addField( FieldForm field)  {
        try {
            FieldDTO savedField = fieldService.save(field);
            ConcurrentHashMap<String, Object> result = new ConcurrentHashMap<>();
            result.put("field", savedField);
            return new ResponseEntity<>(setResponseBody(HttpStatus.OK, "Add field successfully", result), HttpStatus.OK);
        } catch (IllegalArgumentException e){
            log.error(e.getMessage());
            ConcurrentHashMap<String, Object> body = setResponseBody(HttpStatus.BAD_REQUEST, "Wrong user request", null);
            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "{id}/workers")
    public ResponseEntity<Map<String, Object>> addWorker(
            @RequestParam String mode,
            @PathVariable Long id,
            @RequestParam MultipartFile file,
            @RequestParam List<Subject> subjects,
            @ModelAttribute Subject subject
    ) {
        try {
            return switch (mode) {
                case "file" -> addWorkerByFile(id, file);
                case "multi" -> addMultiWorkers(id, subjects);
                case "sole" -> addSoleWorker(id, subject);
                default ->
                        new ResponseEntity<>(setResponseBody(HttpStatus.BAD_REQUEST, "Wrong Request", null), HttpStatus.BAD_REQUEST);
            };
        }catch (NullPointerException e){
            return new ResponseEntity<>(setResponseBody(HttpStatus.BAD_REQUEST, "Wrong Request", null), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("{id}")
    public ResponseEntity<Map<String, Object>> updateField(
            @PathVariable Long id,
            @ModelAttribute FieldForm field) {
        try {
            FieldDTO updatedField = fieldService.update(id, field);
            Map<String, Object> result = new ConcurrentHashMap<>();
            result.put("field", updatedField);
            ConcurrentHashMap<String, Object> body = setResponseBody(HttpStatus.OK, "Update Field success", result);
            return new ResponseEntity<>(body, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(setResponseBody(HttpStatus.BAD_REQUEST, "Wrong Request", null), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Map<String, Object>> deleteField(
            @PathVariable Long id){
        try {
            fieldService.delete(id);
            ConcurrentHashMap<String, Object> body = setResponseBody(HttpStatus.OK, "Delete Field success", null);
            return new ResponseEntity<>(body, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            ConcurrentHashMap<String, Object> body = setResponseBody(HttpStatus.BAD_REQUEST, "Wrong Request", null);
            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * ResponseEntity body를 만들어주는 메소드
     * @param statusCode : HttpStatus
     * @param msg : String
     * @param results : Map&lt;String, Object&gt;
     * @return ConcurrentHashMap&lt;String, Object&gt;
     */
    private static ConcurrentHashMap<String, Object> setResponseBody(HttpStatus statusCode, String msg, Map<String, Object> results) {
        ConcurrentHashMap<String, Object> body= new ConcurrentHashMap<>();
        body.put("statusCode", statusCode);
        body.put("msg", msg);
        if(results!=null){
            body.put("results", results);
        }
        return body;
    }

    /**
     * 피험자를 csv file로 받아서 field에 추가하는 메소드
     * @param id : field id
     * @param file : file
     * @return ResponseEntity
     */
    private ResponseEntity<Map<String, Object>> addWorkerByFile(Long id, MultipartFile file) {
        try {
            FieldDTO foundedField = fieldService.findById(id);

            InputStream inputStream = file.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            List<Subject> subjectList = new ArrayList<>();
            while (bufferedReader.ready()) {
                String line = bufferedReader.readLine();
                String[] split = line.split(",");
                SubjectForm subject = SubjectForm.builder()
                        .name(split[0])
                        .age(Integer.parseInt(split[1]))
                        .detailedJob(DetailedJob.values()[Integer.parseInt(split[2])])
                        .career(Integer.parseInt(split[3]))
                        .remarks(split[4])
                        .risk(Risk.values()[Integer.parseInt(split[5])])
                        .fieldDTO(foundedField)
                        .build();
                Subject savedSubject = subjectService.save(subject);
                subjectList.add(savedSubject);
            }
            Map<String, Object> result = new ConcurrentHashMap<>();
            result.put("subjects", subjectList);
            ConcurrentHashMap<String, Object> body = setResponseBody(HttpStatus.OK, "Add multiple users successfully", result);
            return new ResponseEntity<>(body, HttpStatus.OK);
        }catch (IllegalArgumentException | IOException e) {
            log.error(e.getMessage());
            ConcurrentHashMap<String, Object> body = setResponseBody(HttpStatus.BAD_REQUEST, "Wrong request", null);
            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e){
            log.error(e.getMessage());
            ConcurrentHashMap<String, Object> body = setResponseBody(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server error", null);
            return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 피험자 1명을 필드에 추가함
     * @param id : field id
     * @param subject : 추가할 피험자
     * @return : ResponseEntity
     */
    private ResponseEntity<Map<String, Object>> addSoleWorker(Long id, Subject subject) {
        try {
            FieldDTO foundedField = fieldService.findById(id);
            subject.assignField(foundedField);
            Subject savedSubject = subjectService.save(subject);
            Map<String, Object> result = new ConcurrentHashMap<>();
            result.put("subject", savedSubject);
            ConcurrentHashMap<String, Object> body = setResponseBody(HttpStatus.OK, "Add workers success", result);
            return new ResponseEntity<>(body, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            ConcurrentHashMap<String, Object> body = setResponseBody(HttpStatus.BAD_REQUEST, "Wrong request", null);
            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 여러명의 피험자를 필드에 추가함
     * @param id : field id
     * @param subjects : subject list
     * @return : ResponseEntity
     */
    private ResponseEntity<Map<String, Object>> addMultiWorkers(Long id, List<Subject> subjects) {
        try {
            FieldDTO foundedField = fieldService.findById(id);
            for (Subject subject : subjects) {
                subject.assignField(foundedField);
                Subject savedSubject = subjectService.save(subject);
            }
            Map<String, Object> result = new ConcurrentHashMap<>();
            result.put("subjects", subjects);
            return new ResponseEntity<>(setResponseBody(HttpStatus.OK, "Add multiple Workers success", result), HttpStatus.OK);
        }catch (IllegalArgumentException e){
            log.error("{}", e.getMessage());
            return new ResponseEntity<>(setResponseBody(HttpStatus.BAD_REQUEST, "Wrong Request", null), HttpStatus.BAD_REQUEST);
        }
    }
}
