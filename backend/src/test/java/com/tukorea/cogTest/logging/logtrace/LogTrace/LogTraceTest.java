package com.tukorea.cogTest.logging.logtrace.LogTrace;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogTraceTest {

    @Test
    void logTraceTest(){
        LogTrace logTrace = new LogTrace();
        TraceStatus status = logTrace.begin("logicA");
        A a = new A();
        a.logicA();
        logTrace.end(status);

    }
    @Slf4j
    private static class A{
        public void logicA(){
            log.info("logic A");
        }
    }


}