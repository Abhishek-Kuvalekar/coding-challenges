package cache.cmd.commands;

import cache.cmd.constants.CommandOutputMessage;
import cache.cmd.model.CommandData;
import cache.cmd.model.CommandOutput;
import cache.model.CacheItem;

import java.util.Optional;

public class ReplaceCommand extends SetCommand {
    @Override
    protected CommandOutput execute(CommandData data) {
        Optional<CacheItem> itemOptional = cache.getValue(data.getKey());
        if (itemOptional.isEmpty()) {
            return CommandOutput.builder()
                    .shortOutput(true)
                    .message(CommandOutputMessage.NOT_STORED)
                    .build();
        }

        data.setValue(getUpdatedValue(itemOptional.get(), data));
        data.setByteCount(data.getValue().length());

        return super.execute(data);
    }

    protected String getUpdatedValue(CacheItem item, CommandData data) {
        return data.getValue();
    }
}
