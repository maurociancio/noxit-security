package ar.noxit.security.auth.authorization;

import ar.noxit.security.auth.*;

public class NullAuthorizer implements Authorizer {

    @Override
    public void authorize(String[] serviceRoles) {
    }
}
