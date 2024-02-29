package org.example.cache.cmd.commands;

import org.example.cache.Cache;
import org.example.cache.cmd.constants.CommandOutputMessage;
import org.example.cache.cmd.model.CommandData;
import org.example.cache.cmd.model.CommandOutput;

import java.util.Objects;

public abstract class Command {
    protected Cache cache;

    public Command() {
        this.cache = Cache.getInstance();
    }

    public CommandOutput process(CommandData data) {
        if (Objects.isNull(data)) {
            return CommandOutput.builder()
                    .shortOutput(true)
                    .message(CommandOutputMessage.ERROR)
                    .build();
        }

        return execute(data);
    }

    protected abstract CommandOutput execute(CommandData data);

}
