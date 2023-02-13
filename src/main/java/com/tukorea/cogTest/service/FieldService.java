package com.tukorea.cogTest.service;

import com.tukorea.cogTest.domain.Field;
import com.tukorea.cogTest.domain.FieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FieldService {
    @Autowired
    private  FieldRepository fieldRepository;

    public Field save(Field field) throws IllegalArgumentException{
        return fieldRepository.save(field);
    }

    public Field findById(Long id) throws IllegalArgumentException{
        return fieldRepository.findById(id);
    }
}
