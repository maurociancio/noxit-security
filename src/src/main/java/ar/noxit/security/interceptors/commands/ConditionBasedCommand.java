package ar.noxit.security.interceptors.commands;

import java.lang.reflect.Method;

public abstract class ConditionBasedCommand<T> extends SimpleProxyCommand<T> {

    public ConditionBasedCommand(T proxied) {
        super(proxied);
    }

    @Override
    public Object intercept(T obj, Method method, Object[] args) throws Throwable {
        if (this.shouldInvokeProxy(method)) {
            return super.intercept(obj, method, args);
        } else {
            return this.onInvocationDenied(method);
        }
    }

    abstract protected Object onInvocationDenied(Method method) throws Throwable;

    abstract protected boolean shouldInvokeProxy(Method method);
}
