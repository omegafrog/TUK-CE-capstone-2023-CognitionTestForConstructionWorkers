package com.tukorea.cogTest.dto;

import com.tukorea.cogTest.domain.DetailedJob;
import com.tukorea.cogTest.domain.Field;
import com.tukorea.cogTest.domain.Risk;
import lombok.Builder;
import lombok.Data;

@Data
public class SubjectDTO {
    private Long id;
    private String name;
    private int age;
    private DetailedJob detailedJob;
    private int career;
    private String remarks;
    private Risk risk;
    private Field field;

    @Builder
    public SubjectDTO(Long id, String name, int age, DetailedJob detailedJob, int career, String remarks, Risk risk, Field field) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.detailedJob = detailedJob;
        this.career = career;
        this.remarks = remarks;
        this.risk = risk;
        this.field = field;
    }
}
