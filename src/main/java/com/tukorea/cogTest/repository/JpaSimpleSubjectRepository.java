package com.tukorea.cogTest.repository;

import com.tukorea.cogTest.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaSimpleSubjectRepository extends JpaRepository<Subject, Long> {
}