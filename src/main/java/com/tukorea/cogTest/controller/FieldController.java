package com.tukorea.cogTest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tukorea.cogTest.domain.Subject;
import com.tukorea.cogTest.dto.FieldDTO;
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

import static com.tukorea.cogTest.response.ResponseUtil.setResponseBody;

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
            Map<String, Object> body= switch (mode) {
                case "file" -> fieldService.addWorkerByFile(id, file);
                case "multi" -> fieldService.addMultiWorkers(id, subjects);
                case "sole" -> fieldService.addSoleWorker(id, subject);
                default ->null;
            };
            return new ResponseEntity<>(body, HttpStatus.OK);
        }catch (NullPointerException|IOException e){
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



}
