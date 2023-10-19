package com.tukorea.cogTest.dto;

import com.tukorea.cogTest.domain.enums.DetailedJob;
import com.tukorea.cogTest.domain.Field;
import com.tukorea.cogTest.domain.enums.Risk;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SubjectDTO {
     Long id;
     String name;
     String username;
     String password;
     Integer age;
     DetailedJob detailedJob;
     Integer career;
     String remarks;
     Risk risk;
     FieldDTO field;
     String phoneNum;
     Sex sex;
     LocalDate lastTestedDate;

    @Builder
    public SubjectDTO(Long id, String name, String username, String password, Integer age, DetailedJob detailedJob,
                      Integer career, String remarks, Risk risk, FieldDTO field, Sex sex, String phoneNum, LocalDate lastTestedDate) {
        this.id = id;
        this.name = name;
        this.username= username;
        this.password = password;
        this.age = age;
        this.detailedJob = detailedJob;
        this.career = career;
        this.remarks = remarks;
        this.risk = risk;
        this.field = field;
        this.sex = sex;
        this.phoneNum = phoneNum;
        this.lastTestedDate = lastTestedDate;
    }
}
