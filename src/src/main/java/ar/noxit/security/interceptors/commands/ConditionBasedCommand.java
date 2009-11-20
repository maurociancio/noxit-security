package ar.noxit.security.interceptors.commands;

import ar.noxit.security.exceptions.AuthException;
import java.lang.reflect.Method;

public abstract class ConditionBasedCommand<T> extends SimpleProxyCommand<T> {

    public ConditionBasedCommand(T proxied) {
        super(proxied);
    }

    @Override
    public Object intercept(T obj, Method method, Object[] args) throws Throwable {
        try {
            this.checkInvokeProxy(method);
            return super.intercept(obj, method, args);
        } catch (AuthException e) {
            return this.onInvocationDenied(method, e);
        }
    }

    abstract protected Object onInvocationDenied(Method method, AuthException cause) throws AuthException;

    abstract protected void checkInvokeProxy(Method method) throws AuthException;
}
