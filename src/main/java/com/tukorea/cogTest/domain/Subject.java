package com.tukorea.cogTest.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

@Entity(name = "subject")
@EqualsAndHashCode
public class Subject {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int age;
    private DetailedJob detailedJob;
    private int career;
    private String remarks;
    private Risk risk;

    @ManyToOne
    @JoinColumn(name="field_id")
    private Field field;




}
