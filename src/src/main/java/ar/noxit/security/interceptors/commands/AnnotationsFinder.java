package ar.noxit.security.interceptors.commands;

import ar.noxit.security.annotations.Authenticate;
import ar.noxit.security.annotations.Authorize;
import ar.noxit.security.annotations.Rol;
import ar.noxit.security.exceptions.AuthException;
import ar.noxit.security.exceptions.NoRolException;
import ar.noxit.security.exceptions.NotAuthenticatedException;
import ar.noxit.security.exceptions.NotAuthorizatedException;
import java.lang.reflect.Method;

public class AnnotationsFinder {

    private Class<?> interfaze;

    public AnnotationsFinder(Class<?> interfaze) {
        if (interfaze == null) {
            throw new IllegalArgumentException("Interface cannot be null");
        }
        this.interfaze = interfaze;
    }

    public Authenticate getAuthenticator(Method method) throws AuthException {
        // look for class based or method based authenticate annotation
        Authenticate classAuthAnnotation = interfaze.getAnnotation(Authenticate.class);
        Authenticate methodAuthAnnotation = method.getAnnotation(Authenticate.class);

        // raise an exception if both annotations are null
        if (methodAuthAnnotation == null && classAuthAnnotation == null) {
            throw new NotAuthenticatedException("Neither method=[" + method.getName() + "] nor class=[" +
                    interfaze.getName() + "] has authenticate annotation.");
        }

        // choose method annotation if both are present
        return getWithMethodPriority(methodAuthAnnotation, classAuthAnnotation);
    }

    public Authorize getAuthorizer(Method method) throws AuthException {
        // look for class based or method based authorize annotation
        Authorize classAuthAnnotation = interfaze.getAnnotation(Authorize.class);
        Authorize methodAuthAnnotation = method.getAnnotation(Authorize.class);

        // raise an exception if both annotations are null
        if (methodAuthAnnotation == null && classAuthAnnotation == null) {
            throw new NotAuthorizatedException("Neither method=[" + method.getName() + "] nor class=[" +
                    interfaze.getName() + "] has authorize annotation.");
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
