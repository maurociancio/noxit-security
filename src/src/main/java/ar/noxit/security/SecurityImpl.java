package ar.noxit.security;

import ar.noxit.security.interceptors.Interceptor;
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
    public <T> T secure(final Class<T> interfaze, T proxied) {
        return interceptor.interceptInterface(interfaze,
                new SecurityImplementorCommand<T>(interfaze, proxied));
    }
}
