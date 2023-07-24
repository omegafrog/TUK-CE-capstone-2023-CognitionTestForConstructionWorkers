package com.tukorea.cogTest.dto;


import com.tukorea.cogTest.domain.test.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Setter
@Getter
@NoArgsConstructor
public class TestResultForm {
    LocalDate date;
    Twohand twoHandResult;
    DigitSpan digitSpanResult;
    Conveyor conveyorResult;
    Maze mazeResult;
    DecisionMaking decisionMakingResult;
}
