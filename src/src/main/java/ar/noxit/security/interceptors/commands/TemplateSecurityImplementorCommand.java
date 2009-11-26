package ar.noxit.security.interceptors.commands;

import ar.noxit.security.exceptions.NotAuthenticatedException;
import ar.noxit.security.exceptions.AuthException;
import java.lang.reflect.Method;

public abstract class TemplateSecurityImplementorCommand<T> extends ConditionBasedCommand<T> {

    private Class<T> interfaze;

    public TemplateSecurityImplementorCommand(Class<T> interfaze, T proxied) {
        super(proxied);

        if (interfaze == null) {
            throw new IllegalArgumentException("Interface param cannot be null");
        }

        this.interfaze = interfaze;
    }

    @Override
    protected void checkInvokeProxy(Method method) throws AuthException {
        authenticate();
        authorizate(interfaze, method);
        checkPermissions();
    }

    @Override
    protected Object onInvocationDenied(Method method, AuthException cause) throws AuthException {
        throw new NotAuthenticatedException("Not access to complete this action class=[" +
                interfaze.getName() + "] method=[" + method.getName() + "]", cause);
    }

    protected abstract void authenticate();

    protected abstract void authorizate(Class<T> interfaze, Method method) throws AuthException;

    protected abstract void checkPermissions();
}
