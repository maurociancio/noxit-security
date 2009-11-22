package ar.noxit.security.mocks;

import ar.noxit.security.auth.Authorizer;
import ar.noxit.security.exceptions.NotAuthenticatedException;

public class FailureAuthorizer implements Authorizer {

    @Override
    public void authorize(String[] serviceRoles) throws NotAuthenticatedException {
        throw new NotAuthenticatedException();
    }
}
