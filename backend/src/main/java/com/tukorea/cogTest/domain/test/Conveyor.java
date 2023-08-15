package com.tukorea.cogTest.domain.test;

import jakarta.persistence.Embeddable;
import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@Embeddable
public class Conveyor {
    private Boolean isPassed;
    double elapsedTime;
}
