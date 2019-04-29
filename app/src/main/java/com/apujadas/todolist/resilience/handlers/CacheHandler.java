package com.apujadas.todolist.resilience.handlers;

import com.apujadas.todolist.resilience.annotations.Cache;
import com.apujadas.todolist.resilience.cache.CacheRegistry;
import com.apujadas.todolist.resilience.cache.InvalidCacheException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class CacheHandler extends ResilienceHandler {

    @Override
    public Class<? extends Annotation> classToHandle() {
        return Cache.class;
    }

    @Override
    protected Object handleInternal(Annotation annotation, Object o, Method method, Object[] args) throws Throwable {
        String key = o.getClass().getName() + "." + method.getName();
        Cache cacheAnnotation = (Cache) annotation;
        com.apujadas.todolist.resilience.cache.Cache cache = CacheRegistry.getInstance().getCache(key);
        if (cache == null) {
            try {
                cache = new com.apujadas.todolist.resilience.cache.Cache(cacheAnnotation.provider().newInstance(), cacheAnnotation.expirationPolicy().newInstance());
                CacheRegistry.getInstance().setCache(key, cache);
            } catch (InstantiationException | IllegalAccessException e) {
                return this.successor.handle(o, method, args);
            }
        }
        try {
            return cache.getCache();
        } catch (InvalidCacheException e) {
            return this.successor.handle(o, method, args);
        }
    }

}
