package com.tukorea.cogTest.domain;

import java.util.Optional;

public interface SubjectRepository {
     Subject save(Subject subject);
     Subject update(Long id, Subject subject);
     Subject findById(Long id);

     void delete(Long id);
}

