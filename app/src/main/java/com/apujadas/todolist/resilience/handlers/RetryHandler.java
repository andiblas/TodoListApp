package com.apujadas.todolist.resilience.handlers;

import com.apujadas.todolist.resilience.annotations.Retry;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class RetryHandler extends ResilienceHandler {
    @Override
    public Class<? extends Annotation> classToHandle() {
        return Retry.class;
    }

    @Override
    protected Object handleInternal(Annotation annotation, Object o, Method method, Object[] args) throws Throwable {
        Retry retryAnnotation = (Retry) annotation;
        for (int i = 0; i < retryAnnotation.count(); i++) {
            try {
                return method.invoke(o, args);
            } catch (Exception e) {
                if (i >= retryAnnotation.count()) {
                    throw e;
                }
            }
        }
        return method.invoke(o, args);
    }
}
