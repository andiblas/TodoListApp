package com.apujadas.todolist.resilience.circuitbreaker;

import com.apujadas.todolist.connectivity.ServerException;

import java.util.Timer;
import java.util.TimerTask;

public class CircuitBreaker {

    private Integer errorLimit;
    private Integer timeout;
    private Integer errorCount;
    private CircuitBreakerState state = CircuitBreakerState.CLOSE;

    public CircuitBreaker(Integer errorLimit, Integer timeout) {
        super();
        this.errorLimit = errorLimit;
        this.timeout = timeout;
        this.errorCount = 0;
    }

    public <T> T run(CircuitBreakerCommand<T> command) throws Exception, CircuitBreakerOpenException {
        T result;
        try {
            if (CircuitBreakerState.OPEN.equals(state))
                throw new CircuitBreakerOpenException();

            result = command.execute();

            if (CircuitBreakerState.HALF_OPEN.equals(state)) {
                changeState(CircuitBreakerState.CLOSE);
                errorCount = 0;
            }
        } catch (CircuitBreakerOpenException e) {
            throw e;
        } catch (Exception e) {
            if (this.errorCount < this.errorLimit)
                this.errorCount++;

            if (this.errorCount == this.errorLimit) {
                trip();
                startTimer();
            }
            throw e;
        }
        return result;
    }

    private void startTimer() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                changeState(CircuitBreakerState.HALF_OPEN);
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, timeout);
    }

    private void trip() {
        changeState(CircuitBreakerState.OPEN);
    }

    private void changeState(CircuitBreakerState newState) {
        this.state = newState;
    }

}