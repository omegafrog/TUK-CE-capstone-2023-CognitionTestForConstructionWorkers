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
    private int age;
    private Integer career;
    private Field field;
    private String remarks;
    private Risk risk;

    @Builder
    public UpdateSubjectDto(String name, int age, Integer career, Field field, String remarks, Risk risk) {
        this.name = name;
        this.risk = risk;
        this.age = age;
        this.career = career;
        this.field = field;
        this.remarks = remarks;
    }
}
