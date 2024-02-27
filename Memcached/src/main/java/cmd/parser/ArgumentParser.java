package cmd.parser;


import cmd.constants.CommandLineOptions;
import cmd.constants.DefaultValues;
import cmd.exception.InvalidArgumentException;
import cmd.model.Argument;
import cmd.model.ParsedArguments;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class ArgumentParser {
    public ParsedArguments parseArguments(String[] args) {
        ParsedArguments parsedArguments = getDefaultArgumentValues();

        if (Objects.isNull(args) || args.length == 0) return parsedArguments;

        int index = 0;
        while (index < args.length) {
            switch (args[index]) {
                case CommandLineOptions.SHORT_PORT:
                case CommandLineOptions.LONG_PORT:
                    Argument<Integer> port = parsedArguments.getPort();

                    int portValue = DefaultValues.PORT;
                    try {
                        portValue = Integer.parseInt(args[++index]);
                        if (portValue < 1024 || portValue > 65536) throw new InvalidArgumentException("Invalid port.");
                    } catch (Exception ex) {
                        log.error("Error while parsing port: {}", ex.getMessage());
                        throw new InvalidArgumentException("Invalid port.");
                    }

                    port.setFound(true);
                    port.setValue(portValue);
                    break;

                default:
                    throw new InvalidArgumentException(String.format("Invalid argument: %s", args[index]));
            }
            ++index;
        }

        return parsedArguments;
    }

    private ParsedArguments getDefaultArgumentValues() {
        ParsedArguments parsedArguments = new ParsedArguments();
        parsedArguments.setPort(new Argument<>(false, DefaultValues.PORT));
        return parsedArguments;
    }
}
