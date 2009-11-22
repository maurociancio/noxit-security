package ar.noxit.security.mocks;

import ar.noxit.security.auth.authorization.ArrayBasedAuthorizer;

public class CreateRolAuthorizer extends ArrayBasedAuthorizer {

    public CreateRolAuthorizer() {
        super("create");
    }
}
