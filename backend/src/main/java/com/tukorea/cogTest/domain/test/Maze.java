package com.tukorea.cogTest.domain.test;

import jakarta.persistence.Embeddable;
import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@Embeddable
public class Maze {
    private int collisionCount;
    private Boolean isPassed;
}
