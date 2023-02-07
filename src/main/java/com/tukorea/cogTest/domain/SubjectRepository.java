package com.tukorea.cogTest.domain;

import java.util.Optional;

public interface SubjectRepository {
     Subject save(Subject subject);
     Subject update(Long id, Subject subject);
     Optional<Subject> findById(Long id);
}
