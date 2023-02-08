package com.tukorea.cogTest.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import java.util.Objects;


@Entity
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        Field item = (Field) o;
        return Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return id.intValue();
    }

    public void appendWorkerNum(){
        numOfWorkers++;
    }

    public Field update(Field field){
        this.name = field.getName();
        this.numOfWorkers = field.getNumOfWorkers();
        return this;
    }
}
