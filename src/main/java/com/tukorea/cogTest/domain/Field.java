package com.tukorea.cogTest.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;


@Entity
@EqualsAndHashCode
public class Field {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private int numOfWorkers;


}
