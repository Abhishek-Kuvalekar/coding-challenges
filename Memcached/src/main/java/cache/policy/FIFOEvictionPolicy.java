package cache.policy;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

@Slf4j
public class FIFOEvictionPolicy implements IEvictionPolicy {
    private final Queue<String> queue;
    private final Set<String> set;

    public FIFOEvictionPolicy() {
        queue = new LinkedList<>();
        set = new HashSet<>();
    }

    @Override
    public synchronized void registerKeyAccess(String key) {
        if (set.contains(key)) return;
        queue.offer(key);
    }

    @Override
    public synchronized void removeKey(String key) {
        queue.remove(key);
        set.remove(key);
    }

    @Override
    public synchronized String getKeyToBeEvicted() {
        return queue.peek();
    }
}
