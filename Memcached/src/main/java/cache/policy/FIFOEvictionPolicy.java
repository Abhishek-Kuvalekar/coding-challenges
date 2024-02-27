package cache.policy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FIFOEvictionPolicy implements IEvictionPolicy {
    @Override
    public void registerKeyAccess(String key) {

    }

    @Override
    public String getKeyToBeEvicted() {
        return null;
    }
}
