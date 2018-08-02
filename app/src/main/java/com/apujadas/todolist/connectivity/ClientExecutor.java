package com.apujadas.todolist.connectivity;

import com.apujadas.todolist.domain.ToDo;
import com.apujadas.todolist.resilience.annotations.Retry;
import com.apujadas.todolist.resilience.cache.Cache;
import com.apujadas.todolist.resilience.cache.CacheExpirationPolicy;
import com.apujadas.todolist.resilience.cache.CacheProvider;
import com.apujadas.todolist.resilience.cache.CacheRegistry;
import com.apujadas.todolist.resilience.cache.InvalidCacheException;
import com.apujadas.todolist.resilience.circuitbreaker.CircuitBreaker;
import com.apujadas.todolist.resilience.circuitbreaker.CircuitBreakerRegistry;

import java.io.IOException;
import java.util.List;

public class ClientExecutor {

    private TodoListClient client;

    public ClientExecutor(TodoListClient client) {
        this.client = client;
    }

    public List<ToDo> getAllTodos() throws Exception {
        String methodName = "getAllTodos";
        Cache cache = null;
        AnnotationObtainer annotationObtainer = new AnnotationObtainer(client, methodName);

        com.apujadas.todolist.resilience.annotations.Cache cacheAnnotation;
        if ((cacheAnnotation = annotationObtainer.getAnnotation(com.apujadas.todolist.resilience.annotations.Cache.class)) != null) {
            cache = obtainCache(methodName, cacheAnnotation.provider(), cacheAnnotation.expirationPolicy());
            try {
                return (List<ToDo>) cache.getCache();
            } catch (InvalidCacheException ignored) {
            }
        }

        com.apujadas.todolist.resilience.annotations.CircuitBreaker circuitBreakerAnnotation;
        if ((circuitBreakerAnnotation = annotationObtainer.getAnnotation(com.apujadas.todolist.resilience.annotations.CircuitBreaker.class)) != null) {
            CircuitBreaker cb = obtainCircuitBreaker(methodName, circuitBreakerAnnotation.errorCount(), circuitBreakerAnnotation.timerMiliseconds());
            return processReturn(cb.run(() -> client.getAllTodos()), cache);
        }

        return processReturn(client.getAllTodos(), cache);
    }


    public void addTodo(ToDo todo) throws IOException, ServerException {
        String methodName = "addTodo";
        AnnotationObtainer annotationObtainer = new AnnotationObtainer(client, methodName);

        Retry retryAnnotation;
        if ((retryAnnotation = annotationObtainer.getAnnotation(Retry.class, ToDo.class)) != null) {
            for (int i = 0; i < retryAnnotation.count(); i++) {
                try {
                    client.addTodo(todo);
                    break;
                } catch (Exception e) {
                    if (i >= retryAnnotation.count()) {
                        throw e;
                    }
                }
            }
            return;
        }

        client.addTodo(todo);
    }

    public void enableServer() throws IOException, ServerException {
        String methodName = "enableServer";
        AnnotationObtainer annotationObtainer = new AnnotationObtainer(client, methodName);

        Retry retryAnnotation;
        if ((retryAnnotation = annotationObtainer.getAnnotation(Retry.class, ToDo.class)) != null) {
            for (int i = 0; i < retryAnnotation.count(); i++) {
                try {
                    client.enableServer();
                    break;
                } catch (Exception e) {
                    if (i >= retryAnnotation.count()) {
                        throw e;
                    }
                }
            }
            return;
        }

        client.enableServer();
    }

    public void disableServer() throws IOException, ServerException {
        String methodName = "disableServer";
        AnnotationObtainer annotationObtainer = new AnnotationObtainer(client, methodName);

        Retry retryAnnotation;
        if ((retryAnnotation = annotationObtainer.getAnnotation(Retry.class, ToDo.class)) != null) {
            for (int i = 0; i < retryAnnotation.count(); i++) {
                try {
                    client.disableServer();
                    break;
                } catch (Exception e) {
                    if (i >= retryAnnotation.count()) {
                        throw e;
                    }
                }
            }
            return;
        }

        client.disableServer();
    }


    private <T> T processReturn(T value, Cache cache) {
        if (cache != null)
            cache.setCache(value);
        return value;
    }


    private com.apujadas.todolist.resilience.cache.Cache obtainCache(String key, Class<? extends CacheProvider> provider, Class<? extends CacheExpirationPolicy> expirationPolicy) {
        com.apujadas.todolist.resilience.cache.Cache cache = CacheRegistry.getInstance().getCache(key);
        if (cache != null)
            return cache;

        try {
            cache = new com.apujadas.todolist.resilience.cache.Cache(provider.newInstance(), expirationPolicy.newInstance());
            CacheRegistry.getInstance().setCache(key, cache);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return cache;
    }

    private CircuitBreaker obtainCircuitBreaker(String key, int errorLimit, int timeout) {
        CircuitBreaker result = CircuitBreakerRegistry.getInstance().getCircuitBreaker(key);
        if (result != null)
            return result;
        result = new CircuitBreaker(errorLimit, timeout);
        CircuitBreakerRegistry.getInstance().addCircuitBreaker(key, result);
        return result;
    }
}
