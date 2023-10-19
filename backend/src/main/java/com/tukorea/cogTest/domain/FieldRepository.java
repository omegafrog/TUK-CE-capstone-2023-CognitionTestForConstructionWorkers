package com.tukorea.cogTest.domain;


import java.util.List;

public interface FieldRepository {

    Field save(Field field);
    Field findById(Long id);
    Field update(Long id, Field field);
    void delete(Long id);
    List<Field> findAll();
}
