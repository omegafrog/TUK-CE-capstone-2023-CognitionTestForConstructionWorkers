package com.tukorea.cogTest.domain.test;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Maze extends Test {
    private boolean LT;
    private boolean FT;
}
