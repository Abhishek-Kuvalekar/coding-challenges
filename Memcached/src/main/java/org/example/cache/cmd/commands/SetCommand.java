package org.example.cache.cmd.commands;

import lombok.extern.slf4j.Slf4j;
import org.example.cache.cmd.constants.CommandOutputMessage;
import org.example.cache.cmd.mapper.Mapper;
import org.example.cache.cmd.model.CommandData;
import org.example.cache.cmd.model.CommandOutput;
import org.example.cache.model.CacheItem;

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
