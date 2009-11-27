package ar.noxit.security.auth;

import ar.noxit.security.exceptions.NotAuthenticatedException;

public interface Authenticator {

    void authenticate() throws NotAuthenticatedException;
}
