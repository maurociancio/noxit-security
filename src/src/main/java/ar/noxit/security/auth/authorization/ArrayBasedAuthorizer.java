package ar.noxit.security.auth.authorization;

import ar.noxit.security.auth.Authorizer;
import ar.noxit.security.exceptions.NotAuthorizatedException;

public class ArrayBasedAuthorizer implements Authorizer {

    private final String[] roles;

    public ArrayBasedAuthorizer(String[] roles) {
        if (roles == null) {
            throw new IllegalArgumentException("Roles array cannot be null");
        }
        this.roles = roles;
    }

    public ArrayBasedAuthorizer(String aRol) {
        if (aRol == null) {
            throw new IllegalArgumentException("Rol string cannot be null");
        }
        this.roles = new String[]{aRol};
    }

    @Override
    public void authorize(String[] serviceRoles) throws NotAuthorizatedException {
        Authorizer strategy = serviceRoles.length > 0
                ? getNotEmptyRolesStrategy()
                : getEmptyRolesStrategy();

        strategy.authorize(serviceRoles);
    }

    protected Authorizer getEmptyRolesStrategy() {
        return new NullAuthorizer();
    }

    protected Authorizer getNotEmptyRolesStrategy() {
        return new ArrayNotEmptyAuthorizerStrategy(roles);
    }
}
