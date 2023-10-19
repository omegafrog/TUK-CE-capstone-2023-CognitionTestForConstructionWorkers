package com.tukorea.cogTest.domain.test;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Embeddable
public class DigitSpan{
    private Boolean isPassed;
    public DigitSpan(boolean isPassed) {
        this.isPassed = isPassed;
    }
}
