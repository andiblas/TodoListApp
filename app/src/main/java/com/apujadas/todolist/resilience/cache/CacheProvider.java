package com.apujadas.todolist.resilience.cache;

public interface CacheProvider {
    Object getData();

    void setData(Object data);
}
