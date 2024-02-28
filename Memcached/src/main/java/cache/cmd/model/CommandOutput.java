package cache.cmd.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class CommandOutput {
    private String message;
    private String key;
    private String value;
    private short flag;
    private long byteCount;
    private boolean shortOutput;
}
