package cache.cmd.commands;

import cache.cmd.constants.CommandOutputMessage;
import cache.cmd.mapper.Mapper;
import cache.cmd.model.CommandData;
import cache.cmd.model.CommandOutput;
import cache.model.CacheItem;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Optional;

@Slf4j
public class GetCommand extends Command {
    @Override
    protected CommandOutput execute(CommandData data) {
        Optional<CacheItem> itemOptional = cache.getValue(data.getKey());

        if (itemOptional.isEmpty()) {
            return CommandOutput.builder()
                    .shortOutput(true)
                    .message(CommandOutputMessage.END)
                    .build();
        }

        CommandOutput commandOutput = Mapper.mapCacheItemToCommandOutput(itemOptional.get());
        if (Objects.isNull(commandOutput)) {
            return CommandOutput.builder()
                    .shortOutput(true)
                    .message(CommandOutputMessage.END)
                    .build();
        }

        commandOutput.setMessage(CommandOutputMessage.VALUE);
        commandOutput.setKey(data.getKey());
        commandOutput.setShortOutput(false);

        return commandOutput;
    }
}
