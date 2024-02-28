package cache.cmd;

import cache.cmd.commands.*;
import cache.cmd.constants.CommandOutputMessage;
import cache.cmd.enums.CommandName;
import cache.cmd.model.CommandOutput;
import cache.cmd.model.MemcachedCommand;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class CmdProcessor {
    private static CmdProcessor instance;
    private Map<CommandName, Command> commandMap;

    private CmdProcessor() {
        commandMap = new HashMap<>();
        commandMap.put(CommandName.GET, new GetCommand());
        commandMap.put(CommandName.SET, new SetCommand());
        commandMap.put(CommandName.ADD, new AddCommand());
        commandMap.put(CommandName.REPLACE, new ReplaceCommand());
        commandMap.put(CommandName.APPEND, new AppendCommand());
        commandMap.put(CommandName.PREPEND, new PrependCommand());
    }

    public static CmdProcessor getInstance() {
        if (Objects.nonNull(instance)) return instance;

        synchronized (CmdProcessor.class) {
            if (Objects.nonNull(instance)) return instance;
            instance = new CmdProcessor();
        }

        return instance;
    }

    public CommandOutput process(MemcachedCommand command) {
        if (Objects.isNull(command)) {
            return CommandOutput.builder()
                    .shortOutput(true)
                    .message(CommandOutputMessage.ERROR)
                    .build();
        }

        Command cmd = commandMap.get(command.getName());
        if (Objects.isNull(cmd)) {
            return CommandOutput.builder()
                    .shortOutput(true)
                    .message(CommandOutputMessage.ERROR)
                    .build();
        }

        return cmd.process(command.getData());
    }

}
