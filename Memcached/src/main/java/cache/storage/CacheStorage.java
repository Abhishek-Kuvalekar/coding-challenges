package cache.storage;

import cache.model.CacheItem;
import lombok.extern.slf4j.Slf4j;
import util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class CacheStorage implements ICacheStorage {
    private Map<String, CacheItem> map;
    private int capacity;

    public CacheStorage(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
    }

    @Override
    public synchronized boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public synchronized boolean isFull() {
        return (size() == capacity);
    }

    @Override
    public synchronized long size() {
        return map.size();
    }

    @Override
    public synchronized boolean exists(String key) {
        return map.containsKey(key);
    }

    @Override
    public synchronized boolean insert(String key, CacheItem item) {
        if (StringUtils.isNullOrEmpty(key) || Objects.isNull(item)) return false;
        map.put(key, item);
        return true;
    }

    @Override
    public synchronized boolean delete(String key) {
        if (StringUtils.isNullOrEmpty(key)) return false;
        map.remove(key);
        return true;
    }

    @Override
    public synchronized CacheItem getValue(String key) {
        if (StringUtils.isNullOrEmpty(key)) return null;
        return map.get(key);
    }
}
