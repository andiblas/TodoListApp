package com.apujadas.todolist.resilience.cache;

public interface CacheExpirationPolicy {
    boolean isValid();
    void invalidate();
    void newData();
}
