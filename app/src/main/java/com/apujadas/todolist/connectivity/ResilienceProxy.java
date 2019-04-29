package com.apujadas.todolist.connectivity;

import com.apujadas.todolist.resilience.handlers.CacheHandler;
import com.apujadas.todolist.resilience.handlers.CircuitBreakerHandler;
import com.apujadas.todolist.resilience.handlers.ResilienceHandler;
import com.apujadas.todolist.resilience.handlers.RetryHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ResilienceProxy implements InvocationHandler {

    private ResilienceHandler handler;

    private ResilienceProxy() {
        ResilienceHandler cacheHandler = new CacheHandler();
        ResilienceHandler circuitBreakerHandler = new CircuitBreakerHandler();
        ResilienceHandler retryHandler = new RetryHandler();
        cacheHandler.setSuccessor(circuitBreakerHandler);
        circuitBreakerHandler.setSuccessor(retryHandler);

        handler = cacheHandler;
    }

    public static Object newInstance(Object obj) {
        return java.lang.reflect.Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj
                .getClass().getInterfaces(), new ResilienceProxy());
    }

    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
        try {
            return handler.handle(o, method, args);
        } catch (InvocationTargetException throwable) {
            throw throwable.getCause();
        }
    }
}