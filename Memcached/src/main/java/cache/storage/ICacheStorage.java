package cache.storage;

import cache.model.CacheItem;

public interface ICacheStorage {
    boolean isEmpty();
    long size();
    boolean exists(String key);
    boolean insert(String key, CacheItem item);
    boolean delete(String key);
    CacheItem getValue(String key);

    int capacity();
}
