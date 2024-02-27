package cache.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CacheItem {
    private String data;
    private short flags;
    private long expiry;
    private LocalDateTime createdAt;
}
