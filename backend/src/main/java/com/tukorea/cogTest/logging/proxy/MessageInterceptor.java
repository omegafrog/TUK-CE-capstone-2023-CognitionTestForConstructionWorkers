package com.tukorea.cogTest.logging.proxy;

import com.tukorea.cogTest.logging.logtrace.LogTrace.LogTrace;
import com.tukorea.cogTest.logging.logtrace.LogTrace.TraceStatus;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

@Slf4j
public class MessageInterceptor implements org.aopalliance.intercept.MethodInterceptor {

    private final LogTrace logTrace;

    public MessageInterceptor(LogTrace logTrace) {
        this.logTrace = logTrace;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();

        String className = method.getDeclaringClass().getSimpleName();
        String methodName = method.getName();
        String message = className + "." + methodName + "()";
        TraceStatus status = logTrace.begin(message);
        try
        {
            Object result = invocation.proceed();
            logTrace.end(status);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            logTrace.exception(status, e);
            throw e;
        }
    }
}
