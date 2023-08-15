package com.tukorea.cogTest.domain.test;


import jakarta.persistence.Embeddable;
import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@Embeddable
public class DecisionMaking {
    private Boolean isPassed;
    double minResponseTime;
    int missedGo;
    int missClickedNoGo;
}
