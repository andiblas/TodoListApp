package com.apujadas.todolist.resilience.cache;

public class Cache {

    private CacheProvider provider;
    private CacheExpirationPolicy expirationPolicy;

    public Cache(CacheProvider provider, CacheExpirationPolicy expirationPolicy) {
        this.provider = provider;
        this.expirationPolicy = expirationPolicy;
    }


    public Object getCache() throws InvalidCacheException {
        if (expirationPolicy.isValid())
            return provider.getData();
        throw new InvalidCacheException();
    }

    public void setCache(Object data) {
        provider.setData(data);
        expirationPolicy.newData();
    }
}
