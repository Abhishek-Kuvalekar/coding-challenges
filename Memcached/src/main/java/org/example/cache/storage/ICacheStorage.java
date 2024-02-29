package org.example.cache.storage;

import org.example.cache.model.CacheItem;

public interface ICacheStorage {
    boolean isEmpty();
    boolean isFull();
    long size();
    boolean exists(String key);
    boolean insert(String key, CacheItem item);
    boolean delete(String key);
    CacheItem getValue(String key);
}
