package com.apujadas.todolist.resilience.circuitbreaker;

import java.util.HashMap;

public class CircuitBreakerRegistry {
    private static final CircuitBreakerRegistry ourInstance = new CircuitBreakerRegistry();

    private HashMap<String, CircuitBreaker> registry = new HashMap<>();

    private CircuitBreakerRegistry() {
    }

    public static CircuitBreakerRegistry getInstance() {
        return ourInstance;
    }

    public CircuitBreaker getCircuitBreaker(String key) {
        return registry.get(key);
    }

    public void addCircuitBreaker(String key, CircuitBreaker cb) {
        registry.put(key, cb);
    }
}
