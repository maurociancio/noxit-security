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
        Authenticate classAuthenticateAnnotation = interfaze.getAnnotation(Authenticate.class);
        Authenticate methodAuthenticateAnnotation = method.getAnnotation(Authenticate.class);

        if (methodAuthenticateAnnotation == null && classAuthenticateAnnotation == null) {
            throw new NotAuthenticatedException("Neither method=[" + method.getName() + "] nor clazz=[" +
                    interfaze.getName() + "] has authentication annotation.");
        }

        Authenticate authenticateAnnotation = classAuthenticateAnnotation != null
                ? classAuthenticateAnnotation
                : methodAuthenticateAnnotation;


        Authorizer authorizer = instantiateAuthorizer(authenticateAnnotation);

        Rol rolAnnotation = method.getAnnotation(Rol.class);

        authorizer.authorize(getRolesFrom(rolAnnotation));
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

    private String[] getRolesFrom(Rol rol) {
        if (rol == null) {
            return new String[0];
        } else {
            return rol.roles();
        }
    }
}
