package ar.noxit.security.auth;

import ar.noxit.security.exceptions.NotAuthenticatedException;

public interface Authorizer {

    void authorize(String[] serviceRoles) throws NotAuthenticatedException;
}
