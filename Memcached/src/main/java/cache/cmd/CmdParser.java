package cache.cmd;

import cache.cmd.enums.CommandName;
import cache.cmd.exceptions.CmdParsingException;
import cache.cmd.exceptions.InvalidCmdException;
import cache.cmd.model.CommandData;
import cache.cmd.model.MemcachedCommand;
import lombok.extern.slf4j.Slf4j;
import util.StringUtils;

import java.util.Objects;

@Slf4j
public class CmdParser {
    private static final String NOREPLY = "NOREPLY";
    private static CmdParser instance;

    private CmdParser() { }

    public static CmdParser getInstance() {
        if (Objects.nonNull(instance)) return instance;

        synchronized (CmdParser.class) {
            if (Objects.nonNull(instance)) return instance;
            instance = new CmdParser();
        }

        return  instance;
    }

    public MemcachedCommand parse(String input) {
        if (StringUtils.isNullOrEmpty(input)) return null;

        try {
            String[] chunks = input.split(" ");

            MemcachedCommand command = new MemcachedCommand();
            CommandData data = new CommandData();

            command.setName(getCommandName(chunks[0]));
            data.setKey(chunks[1]);

            if (chunks.length >= 3) data.setFlag(Short.parseShort(chunks[2]));
            if (chunks.length >= 4) data.setExpiryInSeconds(Long.parseLong(chunks[3]));
            if (chunks.length >= 5) data.setByteCount(Long.parseLong(chunks[4]));
            if (chunks.length >= 6) data.setReplyExpected(NOREPLY.equalsIgnoreCase(chunks[5]));

            command.setData(data);

            return command;
        } catch (Exception ex) {
            log.error("Error while parsing input: {}. Error: {}", input, ex.getMessage());
            throw new CmdParsingException(input, ex.getMessage());
        }
    }

    private CommandName getCommandName(String name) {
        if (StringUtils.isNullOrEmpty(name)) throw new InvalidCmdException(name);
        return CommandName.valueOf(name.toUpperCase());
    }

}
