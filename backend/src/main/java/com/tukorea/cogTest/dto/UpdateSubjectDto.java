package com.tukorea.cogTest.dto;

import com.tukorea.cogTest.domain.Field;
import com.tukorea.cogTest.domain.enums.Risk;
import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
public class UpdateSubjectDto {
    private String name;
    private Integer age;
    private Integer career;
    private Field field;
    private String remarks;
    private Risk risk;
    private String phoneNum;

    @Builder
    public UpdateSubjectDto(String name, Integer age, Integer career, Field field, String remarks, Risk risk, String phoneNum) {
        this.name = name;
        this.risk = risk;
        this.age = age;
        this.career = career;
        this.field = field;
        this.remarks = remarks;
        this.phoneNum = phoneNum;
    }
}
