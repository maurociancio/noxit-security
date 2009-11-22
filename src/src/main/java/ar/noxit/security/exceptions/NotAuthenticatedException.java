package ar.noxit.security.exceptions;

public class NotAuthenticatedException extends AuthException {

    public NotAuthenticatedException(Throwable cause) {
        super(cause);
    }

    public NotAuthenticatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotAuthenticatedException(String message) {
        super(message);
    }

    public NotAuthenticatedException() {
    }
}
