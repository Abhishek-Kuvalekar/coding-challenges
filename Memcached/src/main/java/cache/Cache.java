package cache;

import cache.exceptions.KeyNotFoundException;
import cache.model.CacheItem;
import cache.policy.IEvictionPolicy;
import cache.storage.ICacheStorage;
import ioc.DependencyInjector;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;

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
        if (storage.isFull()) evict();

        boolean result = storage.insert(key, item);
        if (!result) return result;

        policy.registerKeyAccess(key);
        return result;
    }

    public boolean removeItem(String key) {
        if (!storage.exists(key)) return true;

        boolean result = storage.delete(key);
        if (!result) return result;

        policy.removeKey(key);
        return result;
    }

    public boolean exists(String key) {
        return (getValue(key).isPresent());
    }

    public Optional<CacheItem> getValue(String key) {
        if (!storage.exists(key)) return Optional.empty();

        CacheItem item = storage.getValue(key);
        policy.registerKeyAccess(key);

        if (isItemExpired(item)) {
            removeItem(key);
            return Optional.empty();
        }

        return Optional.of(item);
    }

    private void evict() {
        while (storage.isFull()) {
            String key = policy.getKeyToBeEvicted();
            log.info("Evicting key: {}", key);
            removeItem(key);
        }
    }

    private boolean isItemExpired(CacheItem item) {
        if (Objects.isNull(item)) return false;
        if (item.getExpiry() < 0) return true;
        if (item.getExpiry() == 0) return false;
        return item.getCreatedAt()
                .plusSeconds(item.getExpiry())
                .isBefore(LocalDateTime.now());
    }
}
