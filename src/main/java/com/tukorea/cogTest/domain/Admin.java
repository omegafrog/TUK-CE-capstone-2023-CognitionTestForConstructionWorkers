package com.tukorea.cogTest.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode
public class Admin {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String position;

    @OneToOne
    @JoinColumn(name="field_id")
    private Field field;


}
