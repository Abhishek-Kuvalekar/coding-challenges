package org.example.cache.policy;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
public class FIFOEvictionPolicy implements IEvictionPolicy {
    private final BlockingQueue<String> queue;
    private final Set<String> set;

    public FIFOEvictionPolicy() {
        queue = new LinkedBlockingQueue<>();
        set = ConcurrentHashMap.newKeySet();
    }

    @Override
    public void registerKeyAccess(String key) {
        if (set.contains(key)) return;
        try {
            queue.put(key);
        } catch (Exception ex) {
            queue.offer(key);
        }
    }

    @Override
    public synchronized void removeKey(String key) {
        queue.remove(key);
        set.remove(key);
    }

    @Override
    public String getKeyToBeEvicted() {
        try {
            return queue.take();
        } catch (Exception ex) {
            return queue.peek();
        }
    }
}
