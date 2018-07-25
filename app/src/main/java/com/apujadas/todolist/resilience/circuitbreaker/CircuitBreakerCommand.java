package com.apujadas.todolist.resilience.circuitbreaker;

public interface CircuitBreakerCommand<T> {
    T execute() throws Exception;
}
