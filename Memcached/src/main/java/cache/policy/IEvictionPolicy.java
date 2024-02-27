package cache.policy;

public interface IEvictionPolicy {
    void registerKeyAccess(String key);

    String getKeyToBeEvicted();
}
