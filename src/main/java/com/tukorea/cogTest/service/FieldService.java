package com.tukorea.cogTest.service;

import com.tukorea.cogTest.domain.Field;
import com.tukorea.cogTest.domain.FieldRepository;
import com.tukorea.cogTest.domain.SubjectRepository;
import com.tukorea.cogTest.dto.FieldDTO;
import com.tukorea.cogTest.dto.FieldForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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


}
