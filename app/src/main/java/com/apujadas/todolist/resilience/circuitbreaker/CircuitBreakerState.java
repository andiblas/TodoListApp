package com.apujadas.todolist.resilience.circuitbreaker;

public enum CircuitBreakerState {
    CLOSE(0), HALF_OPEN(2), OPEN(1);

    private Integer value;

    CircuitBreakerState(Integer value) {
        this.value = value;
    }

}
