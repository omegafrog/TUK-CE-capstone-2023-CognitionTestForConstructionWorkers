package com.tukorea.cogTest.dto;

import com.tukorea.cogTest.domain.enums.DetailedJob;
import com.tukorea.cogTest.domain.enums.Risk;
import lombok.Builder;
import lombok.Data;

@Data
public class SubjectForm {
    private String name;
    private int age;
    private DetailedJob detailedJob;
    private int career;
    private String remarks;
    private Risk risk;
    private FieldDTO fieldDTO;

    @Builder
    public SubjectForm(String name, int age, DetailedJob detailedJob, int career, String remarks, Risk risk, FieldDTO fieldDTO) {
        this.name = name;
        this.age = age;
        this.detailedJob = detailedJob;
        this.career = career;
        this.remarks = remarks;
        this.risk = risk;
        this.fieldDTO = fieldDTO;
    }
}
