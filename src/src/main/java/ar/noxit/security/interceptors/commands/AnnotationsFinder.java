package ar.noxit.security.interceptors.commands;

import ar.noxit.security.annotations.Authorize;
import ar.noxit.security.annotations.Rol;
import ar.noxit.security.auth.Authenticator;
import ar.noxit.security.exceptions.AuthException;
import ar.noxit.security.exceptions.NoRolException;
import ar.noxit.security.exceptions.NotAuthenticatedException;
import java.lang.reflect.Method;

public class AnnotationsFinder {

    private Class<?> interfaze;

    public AnnotationsFinder(Class<?> interfaze) {
        if (interfaze == null) {
            throw new IllegalArgumentException("Interface cannot be null");
        }
        this.interfaze = interfaze;
    }

    public Authenticator getAuthenticator(Method method) {
        return null;
    }

    public Authorize getAuthorizer(Method method) throws AuthException {
        // look for class based or method based authentication annotation
        Authorize classAuthAnnotation = interfaze.getAnnotation(Authorize.class);
        Authorize methodAuthAnnotation = method.getAnnotation(Authorize.class);

        // raise an exception if both annotations are null
        if (methodAuthAnnotation == null && classAuthAnnotation == null) {
            throw new NotAuthenticatedException("Neither method=[" + method.getName() + "] nor class=[" +
                    interfaze.getName() + "] has authentication annotation.");
        }

        // choose method annotation if both are present
        return getWithMethodPriority(methodAuthAnnotation, classAuthAnnotation);
    }

    public Rol getRol(Method method) throws AuthException {
        Rol methodRolAnnotation = method.getAnnotation(Rol.class);
        Rol classRolAnnotation = interfaze.getAnnotation(Rol.class);

        if (methodRolAnnotation == null && classRolAnnotation == null) {
            throw new NoRolException("Neither method=[" + method.getName() + "] nor class=[" +
                    interfaze.getName() + "] has rol annotation.");
        }

        return getWithMethodPriority(methodRolAnnotation, classRolAnnotation);
    }

    private <U> U getWithMethodPriority(U method, U clazz) {
        return method != null
                ? method
                : clazz;
    }
}
