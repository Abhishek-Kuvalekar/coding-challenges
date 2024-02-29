package org.example.cache.cmd.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.cache.cmd.enums.CommandName;

@Getter
@Setter
@ToString
public class MemcachedCommand {
    private CommandName name;
    private CommandData data;
}
