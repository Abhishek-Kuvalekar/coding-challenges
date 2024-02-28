package cache.policy;

public interface IEvictionPolicy {
    void registerKeyAccess(String key);

    void removeKey(String key);

    String getKeyToBeEvicted();
}
