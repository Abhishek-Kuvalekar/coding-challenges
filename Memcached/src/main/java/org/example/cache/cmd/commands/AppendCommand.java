package org.example.cache.cmd.commands;

import org.example.cache.cmd.model.CommandData;
import org.example.cache.model.CacheItem;

public class AppendCommand extends ReplaceCommand {
    @Override
    protected String getUpdatedValue(CacheItem item, CommandData data) {
        return (item.getData() + data.getValue());
    }
}
