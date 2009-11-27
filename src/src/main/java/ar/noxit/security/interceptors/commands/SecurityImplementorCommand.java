package ar.noxit.security.interceptors.commands;

import ar.noxit.security.annotations.Authorize;
import ar.noxit.security.annotations.Rol;
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
    protected void authenticate() {
    }

    @Override
    protected void authorizate(Class<T> interfaze, Method method) throws AuthException {
        // look for authorize annotation
        Authorize authorize = annotationsFinder.getAuthorizer(method);

        // instantiate authorizer
        Authorizer authorizer = instantiateAuthorizer(authorize);

        // look for roles
        Rol rol = null;
        try {
            rol = annotationsFinder.getRol(method);
        } catch (NoRolException ignore) {
        }

        // string roles
        String[] roles = getRolesFrom(rol);

        // try to authorize action
        authorizer.authorize(roles);
    }

    @Override
    protected void checkPermissions() {
    }

    private Authorizer instantiateAuthorizer(Authorize authAnnotation) {
        Class<? extends Authorizer> authorizer = authAnnotation.authorizer();

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
