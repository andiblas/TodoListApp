package com.apujadas.todolist.resilience.handlers;

import com.apujadas.todolist.resilience.annotations.CircuitBreaker;
import com.apujadas.todolist.resilience.circuitbreaker.CircuitBreakerRegistry;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class CircuitBreakerHandler extends ResilienceHandler {

    @Override
    public Class<? extends Annotation> classToHandle() {
        return CircuitBreaker.class;
    }

    @Override
    protected Object handleInternal(Annotation annotation, Object o, Method method, Object[] args) throws Throwable {
        String key = o.getClass().getName() + "." + method.getName();
        CircuitBreaker cbAnnotation = (CircuitBreaker) annotation;
        com.apujadas.todolist.resilience.circuitbreaker.CircuitBreaker circuitBreaker = CircuitBreakerRegistry.getInstance().getCircuitBreaker(key);
        if (circuitBreaker == null) {
            circuitBreaker = new com.apujadas.todolist.resilience.circuitbreaker.CircuitBreaker(cbAnnotation.errorCount(), cbAnnotation.timerMiliseconds());
            CircuitBreakerRegistry.getInstance().addCircuitBreaker(key, circuitBreaker);
        }
        return circuitBreaker.run(() -> method.invoke(o, args));
    }
}
