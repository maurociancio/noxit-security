package ar.noxit.security.interceptors.commands;

import java.lang.reflect.Method;

public abstract class ConditionBasedCommand<T> extends SimpleProxyCommand<T> {

    public ConditionBasedCommand(T proxied) {
        super(proxied);
    }

    @Override
    public Object intercept(T obj, Method method, Object[] args) throws Throwable {
        if (this.shouldInvokeProxy()) {
            return super.intercept(obj, method, args);
        } else {
            return this.onInvocationDenied();
        }
    }

    abstract protected Object onInvocationDenied() throws Throwable;

    abstract protected boolean shouldInvokeProxy();
}
