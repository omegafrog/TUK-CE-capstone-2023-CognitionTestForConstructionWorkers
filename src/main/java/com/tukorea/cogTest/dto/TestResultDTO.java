package com.tukorea.cogTest.dto;

import com.tukorea.cogTest.domain.Subject;
import com.tukorea.cogTest.domain.test.Pvt;
import com.tukorea.cogTest.domain.test.Twohand;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class TestResultDTO {
    private Long id;

    private Subject target;

    private LocalDate date = LocalDate.now();

    private Twohand twohandResult;
    private Pvt pvtResult;
}
