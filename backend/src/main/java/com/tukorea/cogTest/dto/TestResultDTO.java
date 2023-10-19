package com.tukorea.cogTest.dto;

import com.tukorea.cogTest.domain.test.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class TestResultDTO {
    Long id;

    SubjectDTO target;

    LocalDate date;

    Twohand twoHandResult;
    DigitSpan digitSpanResult;
    Conveyor conveyorResult;
    Maze mazeResult;
    DecisionMaking decisionMakingResult;
}
