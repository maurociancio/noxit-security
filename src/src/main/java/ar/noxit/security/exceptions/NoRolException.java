package ar.noxit.security.exceptions;

public class NoRolException extends AuthException {

    public NoRolException() {
    }

    public NoRolException(String message) {
        super(message);
    }

    public NoRolException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoRolException(Throwable cause) {
        super(cause);
    }
}
