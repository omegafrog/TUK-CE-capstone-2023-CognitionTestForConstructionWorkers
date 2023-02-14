package com.tukorea.cogTest.service;

import com.tukorea.cogTest.domain.Field;
import com.tukorea.cogTest.domain.FieldRepository;
import com.tukorea.cogTest.dtos.FieldForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FieldService {
    @Autowired
    private  FieldRepository fieldRepository;

    public Field save(FieldForm fieldForm) throws IllegalArgumentException{
        Field field = Field.builder()
                .name(fieldForm.getName())
                .numOfWorkers(fieldForm.getNumOfWorkers())
                .build();
        return fieldRepository.save(field);
    }

    public Field findById(Long id) throws IllegalArgumentException{
        return fieldRepository.findById(id);
    }

    public Field update(Long id, FieldForm fieldForm){
        Field field = Field.builder()
                .name(fieldForm.getName())
                .numOfWorkers(fieldForm.getNumOfWorkers())
                .build();
        return fieldRepository.update(id, field);
    }

    public void delete(Long id){
        fieldRepository.delete(id);
    }
}
