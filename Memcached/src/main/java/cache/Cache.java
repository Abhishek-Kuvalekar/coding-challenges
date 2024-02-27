package cache;

import cache.model.CacheItem;
import cache.policy.IEvictionPolicy;
import cache.storage.ICacheStorage;
import ioc.DependencyInjector;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class Cache {
    private static Cache instance;

    private ICacheStorage storage;
    private IEvictionPolicy policy;

    private Cache() {
        this.storage = DependencyInjector.getInstance().getCacheStorage();
        this.policy = DependencyInjector.getInstance().getEvictionPolicy();
    }

    public static Cache getInstance() {
        if (Objects.nonNull(instance)) return instance;

        synchronized (Cache.class) {
            if (Objects.nonNull(instance)) return instance;
            instance = new Cache();
        }

        return instance;
    }

    public boolean addItem(String key, CacheItem item) {
        return true;
    }

    public boolean removeItem(String key) {
        return true;
    }

    public boolean exists(String key) {
        return true;
    }

    public CacheItem getValue(String key) {
        return new CacheItem();
    }
}
