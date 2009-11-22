package ar.noxit.security.auth.authentication;

import ar.noxit.security.auth.Authenticator;

public class NullAuthenticator implements Authenticator {

    @Override
    public void authenticate() {
    }
}
