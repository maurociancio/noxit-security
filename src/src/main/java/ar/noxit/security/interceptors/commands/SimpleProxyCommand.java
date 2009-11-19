package ar.noxit.security.interceptors.commands;

import ar.noxit.security.interceptors.InterceptorCommand;
import java.lang.reflect.Method;

public class SimpleProxyCommand<T> implements InterceptorCommand<T> {

    private T proxied;

    public SimpleProxyCommand(T proxied) {
        checkProxied(proxied);

        this.proxied = proxied;
    }

    public T getProxied() {
        return this.proxied;
    }

    public void setProxied(T proxied) {
        checkProxied(proxied);

        this.proxied = proxied;
    }

    @Override
    public Object intercept(T obj, Method method, Object[] args) throws Throwable {
        return method.invoke(this.proxied, args);
    }

    private void checkProxied(T proxied) {
        if (proxied == null) {
            throw new IllegalArgumentException("Proxied object cannot be null");
        }
    }
}
