package com.tukorea.cogTest.service;

import com.tukorea.cogTest.domain.Field;
import com.tukorea.cogTest.domain.FieldRepository;
import com.tukorea.cogTest.domain.Subject;
import com.tukorea.cogTest.domain.SubjectRepository;
import com.tukorea.cogTest.domain.enums.DetailedJob;
import com.tukorea.cogTest.domain.enums.Risk;
import com.tukorea.cogTest.dto.FieldDTO;
import com.tukorea.cogTest.dto.FieldForm;
import com.tukorea.cogTest.dto.SubjectForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class FieldService {
    @Autowired
    private FieldRepository fieldRepository;
    @Autowired
    private SubjectRepository subjectRepository;

    public FieldDTO save(FieldForm fieldForm) throws IllegalArgumentException{
        Field field = Field.builder()
                .name(fieldForm.getName())
                .numOfWorkers(fieldForm.getNumOfWorkers())
                .build();
        return fieldRepository.save(field).toDTO();
    }

    public FieldDTO findById(Long id) throws IllegalArgumentException{
        return fieldRepository.findById(id).toDTO();
    }

    public FieldDTO update(Long id, FieldForm fieldForm){
        Field field = Field.builder()
                .name(fieldForm.getName())
                .numOfWorkers(fieldForm.getNumOfWorkers())
                .build();
        return fieldRepository.update(id, field).toDTO();
    }

    public void delete(Long id){
        fieldRepository.delete(id);
    }

    public Map<String, Object> addWorkerByFile(Long id, MultipartFile file) throws IOException {
        Field foundedField = fieldRepository.findById(id);

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
            Subject savedSubject = subjectRepository.save(subject);
            subjectList.add(savedSubject);
        }
        Map<String, Object> result = new ConcurrentHashMap<>();
        result.put("subjects", subjectList);
        return result;
    }
    public Map<String, Object> addMultiWorkers(Long id, List<Subject> subjects){
        Field foundedField = fieldRepository.findById(id);
        for (Subject subject : subjects) {
            subject.assignField(foundedField);
            Subject savedSubject = subjectRepository.save(subject);
        }
        Map<String, Object> result = new ConcurrentHashMap<>();
        result.put("subjects", subjects);
        return result;
    }
    public Map<String, Object> addSoleWorker(Long id, Subject subject){
        Field foundedField = fieldRepository.findById(id);
        subject.assignField(foundedField);
        Subject savedSubject = subjectRepository.save(subject);
        Map<String, Object> result = new ConcurrentHashMap<>();
        result.put("subject", savedSubject);
        return result;
    }
}
