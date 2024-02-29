package org.example.ioc;

import lombok.extern.slf4j.Slf4j;
import org.example.cache.policy.FIFOEvictionPolicy;
import org.example.cache.policy.IEvictionPolicy;
import org.example.cache.storage.CacheStorage;
import org.example.cache.storage.ICacheStorage;

import java.util.Objects;

@Slf4j
public class DependencyInjector {
    private static DependencyInjector instance;

    private ICacheStorage cacheStorage;

    private IEvictionPolicy evictionPolicy;

    private DependencyInjector() {}

    public static DependencyInjector getInstance() {
        if (Objects.nonNull(instance)) return instance;

        synchronized (DependencyInjector.class) {
            if (Objects.nonNull(instance)) return instance;
            instance = new DependencyInjector();
        }

        return instance;
    }

    public ICacheStorage getCacheStorage() {
        if (Objects.nonNull(cacheStorage)) return cacheStorage;

        synchronized (DependencyInjector.class) {
            if (Objects.nonNull(cacheStorage)) return cacheStorage;
            cacheStorage = new CacheStorage(1000);
        }

        return cacheStorage;
    }

    public IEvictionPolicy getEvictionPolicy() {
        if (Objects.nonNull(evictionPolicy)) return evictionPolicy;

        synchronized (DependencyInjector.class) {
            if (Objects.nonNull(evictionPolicy)) return evictionPolicy;
            evictionPolicy = new FIFOEvictionPolicy();
        }

        return evictionPolicy;
    }

}
