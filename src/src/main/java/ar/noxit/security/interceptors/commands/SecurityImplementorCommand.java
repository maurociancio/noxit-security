package ar.noxit.security.interceptors.commands;

import ar.noxit.security.annotations.Authenticate;
import ar.noxit.security.annotations.Authorize;
import ar.noxit.security.annotations.Rol;
import ar.noxit.security.auth.Authenticator;
import ar.noxit.security.auth.Authorizer;
import ar.noxit.security.exceptions.AuthException;
import ar.noxit.security.exceptions.AuthRuntimeException;
import ar.noxit.security.exceptions.NoRolException;
import java.lang.reflect.Method;

public class SecurityImplementorCommand<T> extends TemplateSecurityImplementorCommand<T> {

    private AnnotationsFinder annotationsFinder;

    public SecurityImplementorCommand(Class<T> interfaze, T proxied) {
        super(interfaze, proxied);

        this.annotationsFinder = new AnnotationsFinder(interfaze);
    }

    @Override
    protected void authenticate(Class<T> interfaze, Method method) throws AuthException {
        // look for authenticate annotation
        Authenticate authenticate = annotationsFinder.getAuthenticator(method);

        // instantiate authenticator
        Authenticator authenticator = instantiateAuthenticator(authenticate);

        // try to authenticate user
        authenticator.authenticate();
    }

    @Override
    protected void authorizate(Class<T> interfaze, Method method) throws AuthException {
        // look for authorize annotation
        Authorize authorize = annotationsFinder.getAuthorizer(method);

        // instantiate authorizer
        Authorizer authorizer = instantiateAuthorizer(authorize);

        // look for roles
        Rol rol = getRol(method);

        // string roles
        String[] roles = getRolesFrom(rol);

        // try to authorize action
        authorizer.authorize(roles);
    }

    @Override
    protected void checkPermissions() {
    }

    private Rol getRol(Method method) throws AuthException {
        try {
            return annotationsFinder.getRol(method);
        } catch (NoRolException ignore) {
            return null;
        }
    }

    private Authorizer instantiateAuthorizer(Authorize authAnnotation) {
        Class<? extends Authorizer> authorizer = authAnnotation.authorizer();
        return instantiate(authorizer);
    }

    private String[] getRolesFrom(Rol rol) {
        if (rol == null) {
            return new String[0];
        } else {
            return rol.roles();
        }
    }

    private Authenticator instantiateAuthenticator(Authenticate authenticate) {
        Class<? extends Authenticator> authenticator = authenticate.authenticator();
        return instantiate(authenticator);
    }

    private <U> U instantiate(Class<U> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception ex) {
            throw new AuthRuntimeException("Cannot instantiate " +
                    clazz.getName() + " using the default constructor", ex);
        }
    }
}
