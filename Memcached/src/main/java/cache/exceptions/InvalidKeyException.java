package cache.exceptions;

public class InvalidKeyException extends RuntimeException {
    public InvalidKeyException(String key) {
        super(String.format("Invalid key: %s", key));
    }
}
