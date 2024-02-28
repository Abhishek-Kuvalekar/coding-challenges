package cache.cmd.exceptions;

public class CmdParsingException extends RuntimeException {
    public CmdParsingException(String input, String message) {
        super(String.format("Parsing failed to input: %s. Error: %s", input, message));
    }
}
