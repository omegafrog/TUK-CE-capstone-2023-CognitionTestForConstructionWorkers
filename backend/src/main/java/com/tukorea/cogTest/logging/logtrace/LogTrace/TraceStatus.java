package com.tukorea.cogTest.logging.logtrace.LogTrace;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TraceStatus {
    private final TraceId traceId;
    private final Long startMilli;

    private String message;
    private String requestUrl;

    public TraceStatus() {
        this.traceId = new TraceId();
        this.startMilli = System.currentTimeMillis();
    }

    public TraceStatus(TraceId traceId, Long startMilli, String message) {
        this.traceId = traceId;
        this.startMilli = startMilli;
        this.message = message;
    }

    public TraceStatus(TraceId traceId, Long startMilli, String message, String requestUrl) {
        this.traceId = traceId;
        this.startMilli = startMilli;
        this.message = message;
        this.requestUrl = requestUrl;
    }
}
