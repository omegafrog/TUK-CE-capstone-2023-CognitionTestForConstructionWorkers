package com.tukorea.cogTest.logging.logtrace.LogTrace;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogTrace {

    private ThreadLocal<TraceStatus> statusHolder = new ThreadLocal<>();

    public TraceStatus begin(String message){
        if(statusHolder.get()==null) {
            statusHolder.set(new TraceStatus());
        }else{
            statusHolder.get().getTraceId().nextTraceId();
        }
        TraceStatus status = statusHolder.get();
        log.info("[{} : level={} | {}] : {}",
                status.getTraceId().getId(), status.getTraceId().getLevel(), status.getRequestUrl(), message);
        return new TraceStatus(status.getTraceId(), status.getStartMilli(), message);
    }

    public void end(TraceStatus status){
        Long endMilli = System.currentTimeMillis();
        Long elapsed = endMilli - status.getStartMilli();
        log.info("[INFO|{} : level={}] : {} elapsed={}",
                status.getTraceId().getId(), status.getTraceId().getLevel(), status.getMessage(), elapsed);
        status.getTraceId().prevTraceId();
    }

    public void exception( TraceStatus status, Exception e) {
        Long endMilli = System.currentTimeMillis();
        Long elapsed = endMilli - status.getStartMilli();
        log.info("[ERROR|{} : level={}] : {} elapsed={}",
                status.getTraceId().getId(), status.getTraceId().getLevel(), status.getMessage(), elapsed);
        if(status.getTraceId().getLevel() != 0){
            status.getTraceId().prevTraceId();
        }
    }
}
