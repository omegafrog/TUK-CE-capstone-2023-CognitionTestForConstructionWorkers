package com.tukorea.cogTest.logging.logtrace.LogTrace;

import lombok.Getter;

import java.util.UUID;

@Getter
public class TraceId {
    private final String id;
    private int level;

    public TraceId() {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.level = 0;
    }

    public void nextTraceId(){
        this.level+=1;
    }
    public void prevTraceId(){
        this.level-=1;
    }

}
