package ar.noxit.security.interceptors.commands;

import ar.noxit.security.annotations.Authenticate;
import ar.noxit.security.annotations.Rol;
import ar.noxit.security.auth.Authorizer;
import ar.noxit.security.exceptions.AuthException;
import ar.noxit.security.exceptions.AuthRuntimeException;
import ar.noxit.security.exceptions.NotAuthenticatedException;
import java.lang.reflect.Method;

public class SecurityImplementorCommand<T> extends TemplateSecurityImplementorCommand<T> {

    public SecurityImplementorCommand(Class<T> interfaze, T proxied) {
        super(interfaze, proxied);
    }

    @Override
    protected void authenticate() {
    }

    @Override
    protected void authorizate(Class<T> interfaze, Method method) throws AuthException {
        // look for authenticate annotation
        Authenticate authenticateAnnotation = getAuthenticateAnnotation(interfaze, method);

        // instantiate authorizer
        Authorizer authorizer = instantiateAuthorizer(authenticateAnnotation);

        // look for roles
        String[] rolesFrom = getRolesFrom(method);

        // try to authorize action
        authorizer.authorize(rolesFrom);
    }

    @Override
    protected void checkPermissions() {
    }

    private Authenticate getAuthenticateAnnotation(Class<T> interfaze, Method method) throws NotAuthenticatedException {
        // look for class based or method based authentication annotation
        Authenticate classAuthenticateAnnotation = interfaze.getAnnotation(Authenticate.class);
        Authenticate methodAuthenticateAnnotation = method.getAnnotation(Authenticate.class);

        // raise an exception if both annotations are null
        if (methodAuthenticateAnnotation == null && classAuthenticateAnnotation == null) {
            throw new NotAuthenticatedException("Neither method=[" + method.getName() + "] nor clazz=[" +
                    interfaze.getName() + "] has authentication annotation.");
        }

        // choose method annotation if both are present
        Authenticate authenticateAnnotation = methodAuthenticateAnnotation != null
                ? methodAuthenticateAnnotation
                : classAuthenticateAnnotation;

        return authenticateAnnotation;
    }

    private Authorizer instantiateAuthorizer(Authenticate authenticateAnnotation) {
        Class<? extends Authorizer> authorizer = authenticateAnnotation.authorizer();

        try {
            return authorizer.newInstance();
        } catch (Exception ex) {
            throw new AuthRuntimeException("Cannot instantiate " +
                    authorizer.getName() + " using the default constructor", ex);
        }
    }

    private String[] getRolesFrom(Method method) {
        Rol rolAnnotation = method.getAnnotation(Rol.class);
        return getRolesFrom(rolAnnotation);
    }

    private String[] getRolesFrom(Rol rol) {
        if (rol == null) {
            return new String[0];
        } else {
            return rol.roles();
        }
    }
}
