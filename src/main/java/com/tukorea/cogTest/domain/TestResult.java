package com.tukorea.cogTest.domain;

import com.tukorea.cogTest.domain.test.Test;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Entity
@EqualsAndHashCode
public class TestResult {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name="subject")
    private Subject target;

    @OneToMany
    private final List<Test> testResults = new ArrayList<>();
}
