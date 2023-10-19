package com.tukorea.cogTest.domain.test;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class Twohand {
    private Boolean isPassed;
    private int failedCount;
    private double minResponseTime;
}
