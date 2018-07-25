package com.apujadas.todolist.resilience.cache;

import java.util.HashMap;

public class CacheRegistry {
    private static final CacheRegistry ourInstance = new CacheRegistry();

    private HashMap<String, Cache> registry = new HashMap<>();

    private CacheRegistry() {
    }

    public static CacheRegistry getInstance() {
        return ourInstance;
    }

    public Cache getCache(String key) {
        return registry.get(key);
    }

    public void setCache(String key, Cache cache) {
        registry.put(key, cache);
    }

}
