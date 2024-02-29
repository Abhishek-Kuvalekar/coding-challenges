package org.example.cache.cmd.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommandData {
    private String key;
    private short flag;
    private long expiryInSeconds;
    private long byteCount;
    private boolean replyExpected;
    private String value;
}
