package org.example.cache.cmd.commands;

import org.example.cache.cmd.constants.CommandOutputMessage;
import org.example.cache.cmd.model.CommandData;
import org.example.cache.cmd.model.CommandOutput;

public class AddCommand extends SetCommand {
    @Override
    protected CommandOutput execute(CommandData data) {
        if (cache.exists(data.getKey())) {
            return CommandOutput.builder()
                    .shortOutput(true)
                    .message(CommandOutputMessage.NOT_STORED)
                    .build();
        }

        return super.execute(data);
    }
}
