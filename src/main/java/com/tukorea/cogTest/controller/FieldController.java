package com.tukorea.cogTest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tukorea.cogTest.domain.Field;
import com.tukorea.cogTest.domain.Subject;
import com.tukorea.cogTest.domain.enums.DetailedJob;
import com.tukorea.cogTest.domain.enums.Risk;
import com.tukorea.cogTest.service.FieldService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    public ResponseEntity<Map<String, Object>> addField(@ModelAttribute Field field, HttpServletResponse response)  {
        try {
            Field savedField = fieldService.save(field);
            ConcurrentHashMap<String, Object> result = new ConcurrentHashMap<>();
            result.put("field", savedField);
            return new ResponseEntity<>(setResponseBody(HttpStatus.OK, "Add field successfully", result), HttpStatus.OK);
        } catch (IllegalArgumentException e){
            log.error(e.getMessage());
            ConcurrentHashMap<String, Object> body = setResponseBody(HttpStatus.BAD_REQUEST, "Wrong user request", null);
            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/{:id}/workers", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> addWorkers(
            @PathVariable Long id,
            @RequestParam MultipartFile file
    ) {
        try {
            Field foundedField = fieldService.findById(id);
            InputStream inputStream = file.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            List<Subject> subjectList = new ArrayList<>();
            while (bufferedReader.ready()) {
                String line = bufferedReader.readLine();
                String[] split = line.split(",");
                Subject subject = Subject.builder()
                        .name(split[0])
                        .age(Integer.parseInt(split[1]))
                        .detailedJob(DetailedJob.values()[Integer.parseInt(split[2])])
                        .career(Integer.parseInt(split[3]))
                        .remarks(split[4])
                        .risk(Risk.values()[Integer.parseInt(split[5])])
                        .field(foundedField)
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

    @PostMapping("/{:id}/workers")
    public ResponseEntity<Map<String, Object>> addWorker(
            @PathVariable Long id,
            @ModelAttribute Subject subject,
            HttpServletResponse response) {

        try {
            Field foundedField = fieldService.findById(id);
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

    private static ConcurrentHashMap<String, Object> setResponseBody(HttpStatus statusCode, String msg, Map<String, Object> results) {
        ConcurrentHashMap<String, Object> body= new ConcurrentHashMap<>();
        body.put("statusCode", statusCode);
        body.put("msg", msg);
        body.put("results", results);
        return body;
    }
}
