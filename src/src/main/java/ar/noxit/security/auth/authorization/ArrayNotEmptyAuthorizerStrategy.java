package ar.noxit.security.auth.authorization;

import ar.noxit.security.auth.Authorizer;
import ar.noxit.security.exceptions.NotAuthorizatedException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ArrayNotEmptyAuthorizerStrategy implements Authorizer {

    private final String[] roles;

    public ArrayNotEmptyAuthorizerStrategy(String[] roles) {
        if (roles == null) {
            throw new IllegalArgumentException("Roles array cannot be null");
        }
        this.roles = roles;
    }

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

    @SuppressWarnings("unchecked")
    private List<String> getServiceRoles(String[] serviceRoles) {
        return serviceRoles != null ? Arrays.asList(serviceRoles) : Collections.EMPTY_LIST;
    }

    private List<String> getRoles() {
        return new LinkedList<String>(Arrays.asList(roles));
    }
}
