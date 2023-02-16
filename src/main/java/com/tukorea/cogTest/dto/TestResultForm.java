package com.tukorea.cogTest.dto;


import com.tukorea.cogTest.domain.Subject;
import com.tukorea.cogTest.domain.test.Pvt;
import com.tukorea.cogTest.domain.test.Twohand;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TestResultForm {
    private SubjectDTO target;

    private LocalDate date = LocalDate.now();

    private Twohand twohandResult;
    private Pvt pvtResult;
}
