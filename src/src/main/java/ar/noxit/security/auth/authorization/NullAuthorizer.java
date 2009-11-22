package ar.noxit.security.auth.authorization;

import ar.noxit.security.auth.Authorizer;

public class NullAuthorizer implements Authorizer {

    @Override
    public void authorize(String[] serviceRoles) {
    }
}
