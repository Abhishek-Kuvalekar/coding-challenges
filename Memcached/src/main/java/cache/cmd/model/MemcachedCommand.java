package cache.cmd.model;

import cache.cmd.enums.CommandName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemcachedCommand {
    private CommandName name;
    private CommandData data;
}
