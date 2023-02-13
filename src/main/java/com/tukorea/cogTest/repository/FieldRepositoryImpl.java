package com.tukorea.cogTest.repository;

import com.tukorea.cogTest.domain.Field;
import com.tukorea.cogTest.domain.FieldRepository;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class FieldRepositoryImpl implements FieldRepository {


    private final JpaSimpleFieldRepository simpleFieldRepository;
    @Override
    public Field save(Field field) throws IllegalArgumentException{
            return simpleFieldRepository.save(field);
    }

    @Override
    public Field findById(Long id) throws IllegalArgumentException  {
        return simpleFieldRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("그런 현장은 없습니다." + id));
    }

    @Override
    public Field update(Long id, Field field) throws IllegalArgumentException {
        Field foundedField = findById(id);
        return foundedField.update(field);
    }

    @Override
    public void delete(Long id) {
        Field foundedField = findById(id);
        simpleFieldRepository.delete(foundedField);
    }
}
