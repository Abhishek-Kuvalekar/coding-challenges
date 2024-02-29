package org.example.cache.cmd.exceptions;

public class InvalidCmdException extends RuntimeException {
    public InvalidCmdException(String name) {
        super(String.format("Invalid command: %s", name));
    }
}
