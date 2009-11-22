package ar.noxit.security.exceptions;

public class AuthException extends Exception {

    public AuthException(Throwable cause) {
        super(cause);
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthException(String message) {
        super(message);
    }

    public AuthException() {
    }
}
