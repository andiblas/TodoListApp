package com.apujadas.todolist.resilience.cache;

public class InMemoryCacheProvider implements CacheProvider {

    private Object cachedData;

    @Override
    public Object getData() {
        return cachedData;
    }

    @Override
    public void setData(Object data) {
        cachedData = data;
    }
}
