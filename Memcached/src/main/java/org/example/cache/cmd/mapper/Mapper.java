package org.example.cache.cmd.mapper;

import org.example.cache.cmd.model.CommandData;
import org.example.cache.cmd.model.CommandOutput;
import org.example.cache.model.CacheItem;
import org.example.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Objects;

public class Mapper {
    public static CacheItem mapCommandDataToCacheItem(CommandData data) {
        if (Objects.isNull(data)) return null;

        CacheItem item = new CacheItem();
        item.setData(data.getValue());
        item.setExpiry(data.getExpiryInSeconds());
        item.setFlags(data.getFlag());
        item.setCreatedAt(LocalDateTime.now());
        return item;
    }

    public static CommandOutput mapCacheItemToCommandOutput(CacheItem item) {
        if (Objects.isNull(item)) return null;

        return CommandOutput.builder()
                .value(item.getData())
                .flag(item.getFlags())
                .byteCount(StringUtils.isNullOrEmpty(item.getData()) ? 0 : item.getData().length())
                .build();
    }
}
