package cache.cmd.commands;

import cache.cmd.constants.CommandOutputMessage;
import cache.cmd.model.CommandData;
import cache.cmd.model.CommandOutput;
import cache.model.CacheItem;

import java.util.Optional;

public class AppendCommand extends ReplaceCommand {
    @Override
    protected String getUpdatedValue(CacheItem item, CommandData data) {
        return (item.getData() + data.getValue());
    }
}
