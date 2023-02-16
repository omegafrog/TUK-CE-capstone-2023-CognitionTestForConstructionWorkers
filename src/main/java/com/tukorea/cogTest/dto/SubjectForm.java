package com.tukorea.cogTest.dto;

import com.tukorea.cogTest.domain.enums.DetailedJob;
import lombok.Builder;
import lombok.Data;

@Data
public class SubjectForm {
    private String name;
    private int age;
    private DetailedJob detailedJob=DetailedJob.COMMON;
    private int career;
    private String remarks;

    @Builder
    public SubjectForm(String name, int age, DetailedJob detailedJob, int career, String remarks) {
        this.name = name;
        this.age = age;
        this.detailedJob = detailedJob;
        this.career = career;
        this.remarks = remarks;
    }
}
