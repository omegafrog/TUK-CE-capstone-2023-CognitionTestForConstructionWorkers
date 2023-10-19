package com.tukorea.cogTest.domain;

import com.tukorea.cogTest.dto.NotPassedCountDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class NotPassedCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "FIELD_ID")
    private Field field;

    private Long count=0L;

    private LocalDate date = LocalDate.now();

    public void increaseCount(){ count++;}

    public NotPassedCount(Field field) {
        this.field = field;
    }

    public NotPassedCountDto toDto(){
        return NotPassedCountDto.builder()
                .id(id)
                .date(date)
                .count(count)
                .field(field.toDTO())
                .build();

    }
}
