package com.tukorea.cogTest.domain.test;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode
public class Test {

    @Id
    @GeneratedValue
    private Long id;
    public Test() {
    }
}
