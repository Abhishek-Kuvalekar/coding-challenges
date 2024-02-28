package cache.cmd.commands;

import cache.Cache;
import cache.cmd.constants.CommandOutputMessage;
import cache.cmd.model.CommandData;
import cache.cmd.model.CommandOutput;
import cache.cmd.model.MemcachedCommand;
import cmd.constants.CommandLineOptions;

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
