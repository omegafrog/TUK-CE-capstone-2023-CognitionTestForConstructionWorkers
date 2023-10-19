package com.tukorea.cogTest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tukorea.cogTest.dto.FieldDTO;
import com.tukorea.cogTest.dto.FieldForm;
import com.tukorea.cogTest.response.ResponseUtil;
import com.tukorea.cogTest.service.FieldService;
import com.tukorea.cogTest.service.SubjectService;
import com.tukorea.cogTest.service.SubjectServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.tukorea.cogTest.response.ResponseUtil.setResponseBody;

@RequestMapping("/site")
@ResponseBody
@RequiredArgsConstructor
@RestController
public class FieldController {

    private final FieldService fieldService;
    private final ObjectMapper objectMapper;


    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getAllFields(){
        List<FieldDTO> all = fieldService.findAll();

        Map<String, Object> data = new HashMap<>();
        data.put("fields", all);
        ConcurrentHashMap<String, Object> body = setResponseBody(HttpStatus.OK, "Find all field success", data);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Map<String, Object>> addField( FieldForm field)  {
        try {
            FieldDTO savedField = fieldService.save(field);
            ConcurrentHashMap<String, Object> result = new ConcurrentHashMap<>();
            result.put("field", savedField);
            return new ResponseEntity<>(setResponseBody(HttpStatus.OK, "Add field successfully", result), HttpStatus.OK);
        } catch (IllegalArgumentException e){
            return ResponseUtil.returnWrongRequestErrorResponse(e);
        }catch (RuntimeException e){
            return ResponseUtil.setInternalErrorResponse(e);
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
            HttpHeaders httpHeader = new HttpHeaders();
            httpHeader.setContentType(MediaType.APPLICATION_JSON);
            return ResponseEntity
                    .ok()
                    .headers(httpHeader)
                    .body(body);
        } catch (IllegalArgumentException e) {
            HttpHeaders httpHeader = new HttpHeaders();
            httpHeader.setContentType(MediaType.APPLICATION_JSON);
            return ResponseEntity.
                    badRequest()
                    .headers(httpHeader)
                    .body(setResponseBody(HttpStatus.BAD_REQUEST, e.getMessage(), null));
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Map<String, Object>> deleteField(
            @PathVariable Long id){
        try {
            fieldService.delete(id);
            ConcurrentHashMap<String, Object> body = setResponseBody(HttpStatus.OK, "Delete Field success", null);
            return new ResponseEntity<>(body, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ConcurrentHashMap<String, Object> body = setResponseBody(HttpStatus.BAD_REQUEST, "Wrong Request", null);
            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        }
    }



}
