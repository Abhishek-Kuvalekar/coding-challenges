package org.example.cache.exceptions;

public class KeyNotFoundException extends RuntimeException {
    public KeyNotFoundException(String key) {
        super(String.format("Key does not exist: %s", key));
    }
}
