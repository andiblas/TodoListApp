package com.apujadas.todolist.resilience.annotations;

import com.apujadas.todolist.resilience.cache.CacheExpirationPolicy;
import com.apujadas.todolist.resilience.cache.CacheProvider;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache {
    Class<? extends CacheProvider> provider();

    Class<? extends CacheExpirationPolicy> expirationPolicy();
}
