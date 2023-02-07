package com.tukorea.cogTest.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@EqualsAndHashCode
@NoArgsConstructor
@Getter
public class Field {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private int numOfWorkers;

    @Builder
    public Field(Long id, String name, int numOfWorkers) {
        this.id = id;
        this.name = name;
        this.numOfWorkers = numOfWorkers;
    }
}
