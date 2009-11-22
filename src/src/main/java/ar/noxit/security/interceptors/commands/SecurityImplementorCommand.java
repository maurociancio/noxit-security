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

        Authorizer authorizer = instantiateAuthorizer(authenticateAnnotation);
        String[] rolesFrom = getRolesFrom(method);

        authorizer.authorize(rolesFrom);
    }

    @Override
    protected void checkPermissions() {
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
