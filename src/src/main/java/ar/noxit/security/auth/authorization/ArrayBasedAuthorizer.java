package ar.noxit.security.auth.authorization;

import ar.noxit.security.auth.Authorizer;
import ar.noxit.security.exceptions.NotAuthorizatedException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

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
        return new Authorizer() {

            @Override
            public void authorize(String[] serviceRoles) throws NotAuthorizatedException {
                List<String> rolesList = getRoles();
                List<String> serviceRolesList = getServiceRoles(serviceRoles);

                rolesList.retainAll(serviceRolesList);

                if (rolesList.isEmpty()) {
                    throw new NotAuthorizatedException("Not authorized to complete this action because the " +
                            "roles do not match (required=[" + roles + "] provided=[" + serviceRoles + "])");
                }
            }
        };
    }

    private List<String> getRoles() {
        return new LinkedList<String>(Arrays.asList(roles));
    }

    @SuppressWarnings("unchecked")
    private List<String> getServiceRoles(String[] serviceRoles) {
        return serviceRoles != null ? Arrays.asList(serviceRoles) : Collections.EMPTY_LIST;
    }
}
