package cache.cmd.commands;

import cache.cmd.constants.CommandOutputMessage;
import cache.cmd.model.CommandData;
import cache.cmd.model.CommandOutput;

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
