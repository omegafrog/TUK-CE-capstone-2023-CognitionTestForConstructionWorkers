package com.tukorea.cogTest.domain;

import com.tukorea.cogTest.domain.enums.DetailedJob;
import com.tukorea.cogTest.domain.enums.Risk;
import com.tukorea.cogTest.dto.SubjectDTO;
import jakarta.persistence.*;
import lombok.Builder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity(name = "subject")

@NoArgsConstructor
@Getter
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

    @Builder
    public Subject(Long id, String name, int age, DetailedJob detailedJob, int career, String remarks, Risk risk, Field field) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.detailedJob = detailedJob;
        this.career = career;
        this.remarks = remarks;
        this.risk = risk;
        this.field = field;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        Subject item = (Subject) o;
        return Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return id.intValue();
    }

    public Subject update(SubjectDTO subject){
        this.name = subject.getName();
        this.age = subject.getAge();
        this.career = subject.getCareer();
        this.detailedJob = subject.getDetailedJob();
        this.field = subject.getField();
        this.remarks = subject.getRemarks();
        return this;
    }

    public void assignField(Field field){
        this.field = field;
    }

    public SubjectDTO toDTO(){
        return SubjectDTO.builder()
                .id(this.id)
                .age(this.age)
                .name(this.name)
                .career(this.career)
                .detailedJob(this.detailedJob)
                .field(this.field)
                .remarks(this.remarks)
                .risk(this.risk)
                .build();
    }
}
