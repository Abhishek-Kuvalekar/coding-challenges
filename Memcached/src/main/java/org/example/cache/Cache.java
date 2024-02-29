package org.example.cache;

import lombok.extern.slf4j.Slf4j;
import org.example.cache.model.CacheItem;
import org.example.cache.policy.IEvictionPolicy;
import org.example.cache.storage.ICacheStorage;
import org.example.ioc.DependencyInjector;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

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

    public synchronized boolean addItem(String key, CacheItem item) {
        if (storage.isFull()) evict();

        boolean result = storage.insert(key, item);
        if (!result) return result;

        policy.registerKeyAccess(key);
        return result;
    }

    public synchronized boolean removeItem(String key) {
        if (!storage.exists(key)) return true;

        boolean result = storage.delete(key);
        if (!result) return result;

        policy.removeKey(key);
        return result;
    }

    public synchronized boolean exists(String key) {
        return (getValue(key).isPresent());
    }

    public synchronized Optional<CacheItem> getValue(String key) {
        if (!storage.exists(key)) return Optional.empty();

        CacheItem item = storage.getValue(key);
        policy.registerKeyAccess(key);

        if (isItemExpired(item)) {
            removeItem(key);
            return Optional.empty();
        }

        return Optional.of(item);
    }

    private synchronized void evict() {
        while (storage.isFull()) {
            String key = policy.getKeyToBeEvicted();
            log.info("Evicting key: {}", key);
            removeItem(key);
        }
    }

    private synchronized boolean isItemExpired(CacheItem item) {
        if (Objects.isNull(item)) return false;
        if (item.getExpiry() < 0) return true;
        if (item.getExpiry() == 0) return false;
        return item.getCreatedAt()
                .plusSeconds(item.getExpiry())
                .isBefore(LocalDateTime.now());
    }
}
