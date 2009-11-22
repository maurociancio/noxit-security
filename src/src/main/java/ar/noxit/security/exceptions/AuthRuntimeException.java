package ar.noxit.security.exceptions;

public class AuthRuntimeException extends RuntimeException {

    public AuthRuntimeException(Throwable cause) {
        super(cause);
    }

    public AuthRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthRuntimeException(String message) {
        super(message);
    }

    public AuthRuntimeException() {
    }
}
