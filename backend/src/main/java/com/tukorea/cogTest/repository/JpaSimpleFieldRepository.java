package com.tukorea.cogTest.repository;

import com.tukorea.cogTest.domain.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaSimpleFieldRepository extends JpaRepository<Field, Long> {

}
