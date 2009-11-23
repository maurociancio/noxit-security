package ar.noxit.security.interceptors.commands;

import ar.noxit.security.annotations.Auth;
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
        Auth authAnnotation = getAuthenticateAnnotation(interfaze, method);

        // instantiate authorizer
        Authorizer authorizer = instantiateAuthorizer(authAnnotation);

        // look for roles
        String[] roles = getRolesFrom(interfaze, method);

        // try to authorize action
        authorizer.authorize(roles);
    }

    @Override
    protected void checkPermissions() {
    }

    private Auth getAuthenticateAnnotation(Class<T> interfaze, Method method) throws NotAuthenticatedException {
        // look for class based or method based authentication annotation
        Auth classAuthAnnotation = interfaze.getAnnotation(Auth.class);
        Auth methodAuthAnnotation = method.getAnnotation(Auth.class);

        // raise an exception if both annotations are null
        if (methodAuthAnnotation == null && classAuthAnnotation == null) {
            throw new NotAuthenticatedException("Neither method=[" + method.getName() + "] nor clazz=[" +
                    interfaze.getName() + "] has authentication annotation.");
        }

        // choose method annotation if both are present
        return getWithMethodPriority(methodAuthAnnotation, classAuthAnnotation);
    }

    private Authorizer instantiateAuthorizer(Auth authAnnotation) {
        Class<? extends Authorizer> authorizer = authAnnotation.authorizer();

        try {
            return authorizer.newInstance();
        } catch (Exception ex) {
            throw new AuthRuntimeException("Cannot instantiate " +
                    authorizer.getName() + " using the default constructor", ex);
        }
    }

    private String[] getRolesFrom(Class<T> interfaze, Method method) {
        Rol methodRolAnnotation = method.getAnnotation(Rol.class);
        Rol classRolAnnotation = interfaze.getAnnotation(Rol.class);

        Rol rolAnnotation = getWithMethodPriority(methodRolAnnotation, classRolAnnotation);
        return getRolesFrom(rolAnnotation);
    }

    private <U> U getWithMethodPriority(U method, U clazz) {
        return method != null
                ? method
                : clazz;
    }

    private String[] getRolesFrom(Rol rol) {
        if (rol == null) {
            return new String[0];
        } else {
            return rol.roles();
        }
    }
}
