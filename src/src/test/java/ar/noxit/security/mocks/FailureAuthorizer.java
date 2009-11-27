package ar.noxit.security.mocks;

import ar.noxit.security.auth.Authorizer;
import ar.noxit.security.exceptions.NotAuthorizatedException;

public class FailureAuthorizer implements Authorizer {

    @Override
    public void authorize(String[] serviceRoles) throws NotAuthorizatedException {
        throw new NotAuthorizatedException();
    }
}
