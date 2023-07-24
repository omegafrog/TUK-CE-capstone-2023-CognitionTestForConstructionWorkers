package com.tukorea.cogTest.domain.test;

import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class Test {

    private boolean isPassed;

    public Test(boolean isPassed) {
        this.isPassed = isPassed;
    }

}
