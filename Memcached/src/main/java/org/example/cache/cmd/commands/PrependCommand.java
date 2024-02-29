package org.example.cache.cmd.commands;

import org.example.cache.cmd.model.CommandData;
import org.example.cache.model.CacheItem;

public class PrependCommand extends ReplaceCommand {
    @Override
    protected String getUpdatedValue(CacheItem item, CommandData data) {
        return (data.getValue() + item.getData());
    }
}
