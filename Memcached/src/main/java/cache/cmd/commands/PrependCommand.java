package cache.cmd.commands;

import cache.cmd.constants.CommandOutputMessage;
import cache.cmd.model.CommandData;
import cache.cmd.model.CommandOutput;
import cache.model.CacheItem;

import java.util.Optional;

public class PrependCommand extends ReplaceCommand {
    @Override
    protected String getUpdatedValue(CacheItem item, CommandData data) {
        return (data.getValue() + item.getData());
    }
}
