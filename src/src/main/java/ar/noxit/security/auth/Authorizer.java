package ar.noxit.security.auth;

import ar.noxit.security.exceptions.NotAuthorizatedException;

public interface Authorizer {

    void authorize(String[] serviceRoles) throws NotAuthorizatedException;
}
