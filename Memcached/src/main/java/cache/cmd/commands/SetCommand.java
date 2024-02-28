package cache.cmd.commands;

import cache.cmd.constants.CommandOutputMessage;
import cache.cmd.mapper.Mapper;
import cache.cmd.model.CommandData;
import cache.cmd.model.CommandOutput;
import cache.cmd.model.MemcachedCommand;
import cache.model.CacheItem;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class SetCommand extends Command {
    @Override
    protected CommandOutput execute(CommandData data) {
        CacheItem item = Mapper.mapCommandDataToCacheItem(data);
        boolean result = cache.addItem(data.getKey(), item);

        return CommandOutput.builder()
                .shortOutput(true)
                .message(result ? CommandOutputMessage.STORED : CommandOutputMessage.ERROR)
                .build();
    }
}
