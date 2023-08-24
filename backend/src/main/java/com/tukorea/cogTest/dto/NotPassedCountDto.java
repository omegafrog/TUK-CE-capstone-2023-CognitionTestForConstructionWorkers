package com.tukorea.cogTest.dto;

import com.tukorea.cogTest.domain.Field;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Setter
@Getter
@NoArgsConstructor
public class NotPassedCountDto {
    private Long id;
    private FieldDTO field;
    private Long count;
    private LocalDate date;

    @Builder
    public NotPassedCountDto(Long id, FieldDTO field, Long count, LocalDate date) {
        this.id = id;
        this.field = field;
        this.count = count;
        this.date = date;
    }
}
