package ar.noxit.security.exceptions;

public class NotAuthorizatedException extends AuthException {

    public NotAuthorizatedException() {
    }

    public NotAuthorizatedException(String message) {
        super(message);
    }

    public NotAuthorizatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotAuthorizatedException(Throwable cause) {
        super(cause);
    }
}
