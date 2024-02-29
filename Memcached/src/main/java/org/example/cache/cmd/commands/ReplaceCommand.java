package org.example.cache.cmd.commands;

import org.example.cache.cmd.constants.CommandOutputMessage;
import org.example.cache.cmd.model.CommandData;
import org.example.cache.cmd.model.CommandOutput;
import org.example.cache.model.CacheItem;

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
