package ar.noxit.security;

import ar.noxit.security.interceptors.Interceptor;
import ar.noxit.security.interceptors.InterceptorCommand;
import ar.noxit.security.interceptors.commands.SecurityImplementorCommand;

public class SecurityImpl implements Security {

    private Interceptor interceptor;

    public SecurityImpl(Interceptor interceptor) {
        if (interceptor == null) {
            throw new IllegalArgumentException("Interceptor cannot be null");
        }
        this.interceptor = interceptor;
    }

    @Override
    public <T> T secure(Class<T> interfaze, T proxied) {
        return interceptor.interceptInterface(interfaze, getInterceptorCommand(interfaze, proxied));
    }

    protected <T> InterceptorCommand<T> getInterceptorCommand(Class<T> interfaze, T proxied) {
        return new SecurityImplementorCommand<T>(interfaze, proxied);
    }
}
