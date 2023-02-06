package com.tukorea.cogTest.domain.test;

public class Twohand extends Test{
    private Long totalMeanDuration;
    private Long totalMeanError;

    public Twohand(Long totalMeanDuration, Long totalMeanError) {
        this.totalMeanDuration = totalMeanDuration;
        this.totalMeanError = totalMeanError;
    }
}
